package com.mugbya.cjtrade.business.member.service;


import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.business.member.dao.MemberDao;
import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.utils.WebUtil;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Resource
    private StrongUuidGenerator idGenerator;

    @Resource
    private ProcessEngineCore processEngineCore;

    @Resource
    private MemberWorkflowService memberWorkflowService;

    @Override
    public void start(Member member, Map<String, Object> variables) {

        // 使用activiti的自增长器
        member.setMemberid(idGenerator.getNextId());

        try {
            memberDao.save(member);
            memberWorkflowService.startProcess("activitiDemo_v4", member.getMemberid(), variables);
        } catch (Exception e) {
            System.out.println("异常 : " + e);
        }
    }

    /**
     * 查询代办任务
     * @param dto
     * @param request
     * @return
     */
    @Override
    public List<Member> UsertaskList(Dto dto, HttpServletRequest request) {
        List<Member> members = new ArrayList<>();

        String userId = WebUtil.getLoginUser(request).getUsername();
        // 根据当前人的ID查询代办任务
        List<Task> taskList = memberWorkflowService.taskList(userId);
        System.out.println("任务列表长度是 : " + taskList.size());

        // 根据流程的业务ID查询实体并关联
        for (Task task : taskList) {
            ProcessInstance processInstance = processEngineCore.queryProcessInstance(task.getProcessInstanceId());
            String businessKey = processInstance.getBusinessKey();
//            String applyUser = processEngineCore.getApplyUser(task.getId()).toString();
//            String reason = processEngineCore.getReason(task.getId());

            String applyUser = processEngineCore.getVariable(task.getId(),"initiator");
            String mugbya_reason = processEngineCore.getVariable(task.getId(),"mugbya_reason");
            String yeats_reason = processEngineCore.getVariable(task.getId(),"yeats_reason");

            if (businessKey == null) {
                continue;
            }
            Member member = getMember(businessKey);

            member.setApplyUser(applyUser);
            member.setAssignee(task.getAssignee());
            member.setTaskId(task.getId());
            member.setTaskName(task.getName());
            if (yeats_reason != null){
                member.setReason(yeats_reason);
            }else {
                member.setReason(mugbya_reason);
            }
            members.add(member);
        }

        return members;
    }

    @Override
    public void handlerTask(String taskId, String userId, Boolean value, String reason) {
        memberWorkflowService.handlerTask(taskId, userId, value, reason);
    }

    @Override
    public void revision(Member member, String taskId, Boolean value) {

        if (value == true){
            Member member1 = memberDao.getMemberById(member.getMemberid());

            member1.setMembername(member.getMembername());
            member1.setMemberphone(member.getMemberphone());
            member1.setMemberemail(member.getMemberemail());
            member1.setDeptid(member.getDeptid());
            memberDao.update(member1);
        }else {
            // 删除member
        }

        memberWorkflowService.revision(taskId,  value);
    }

    @Override
    public void saveMember(Member member) {

    }

    @Override
    public Member getMember(String memberId) {
        Member member = memberDao.getMemberById(memberId);
        System.out.println("查到的member " + member);
        return member;
    }

    @Override
    public List<Member> getAllMember() {
        List<HistoricProcessInstance> historicProcessInstances = processEngineCore.getAllProcessInstance();

        for (HistoricProcessInstance pro : historicProcessInstances) {
            String businessKey = pro.getBusinessKey();
            System.out.println("业务ID : " + businessKey);
            Member member = getMember(businessKey);
        }
        return null;
    }
}
