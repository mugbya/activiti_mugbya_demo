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

    @Override
    public void start(Member member, Map<String, Object> variables) {

        // 使用activiti的自增长器
        member.setMemberid(idGenerator.getNextId());

        try {
            memberDao.save(member);
            processEngineCore.startInstance("activitiDemo_v4", member.getMemberid(), variables);
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
        List<Task> taskList = processEngineCore.queryTaskList(userId);
        System.out.println("任务列表长度是 : " + taskList.size());

        // 根据流程的业务ID查询实体并关联
        for (Task task : taskList) {
//            String processInstanceId = task.getProcessInstanceId();
//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
//            String businessKey = processInstance.getBusinessKey();
            ProcessInstance processInstance = processEngineCore.queryProcessInstance(task.getProcessInstanceId());
            String businessKey = processInstance.getBusinessKey();
            String applyUser = processEngineCore.getApplyUser(task.getId()).toString();
            String reason = processEngineCore.getReason(task.getId());

            if (businessKey == null) {
                continue;
            }
            Member member = getMember(businessKey);

            member.setApplyUser(applyUser);
            member.setAssignee(task.getAssignee());
            member.setTaskId(task.getId());
            member.setTaskName(task.getName());
            member.setReason(reason);
            members.add(member);
        }

        return members;
    }

    @Override
    public void handlerTask(String taskId, String userId, Boolean variables) {
        processEngineCore.handlerUserTask(taskId, userId, variables);
    }


    @Override
    public void handlerTask(String taskId, String userId, Boolean variables, String reason) {
        processEngineCore.handlerUserTask(taskId, userId, variables,reason);
    }

    @Override
    public void reApply(Member member, String taskId, String userId, Boolean variables) {

        Member member1 = memberDao.getMemberById(member.getMemberid());

        member1.setMembername(member.getMembername());
        member1.setMemberphone(member.getMemberphone());
        member1.setMemberemail(member.getMemberemail());
        member1.setDeptid(member.getDeptid());
        memberDao.update(member1);

        processEngineCore.handlerUserTask(taskId, userId, variables);
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
