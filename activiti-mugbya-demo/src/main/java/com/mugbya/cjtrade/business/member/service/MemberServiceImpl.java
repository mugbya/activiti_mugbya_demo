package com.mugbya.cjtrade.business.member.service;


import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.business.member.dao.MemberDao;
import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.utils.WebUtil;
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

    static Integer memberid = 0;

    List<Member> memberList = new ArrayList<>();

    @Resource
    private ProcessEngineCore processEngineCore;

    @Override
    public void start(Member member, Map<String,Object> variables) {
        member.setMemberid(setMemberId());
        try {
            memberDao.save(member);
            processEngineCore.startInstance("activitiDemo_v3",member.getMemberid(),variables);
        }catch (Exception e){
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
        for (Task task : taskList){
//            String processInstanceId = task.getProcessInstanceId();
//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
//            String businessKey = processInstance.getBusinessKey();
            ProcessInstance processInstance = processEngineCore.queryProcessInstance(task.getProcessInstanceId());
            String   businessKey = processInstance.getBusinessKey();
            String applyUser = processEngineCore.getApplyUser(task.getId()).toString();

            if (businessKey == null) {
                continue;
            }
            Member member = getMember(businessKey);

            member.setApplyUser(applyUser);
            member.setAssignee(task.getAssignee());
            member.setTaskId(task.getId());
            members.add(member);
        }

        return members;
    }

    @Override
    public void handlerTask(String taskId, String userId) {
        processEngineCore.handlerUserTask(taskId,userId);
    }

    @Override
    public void saveMember(Member member) {

    }

    public  String  setMemberId(){
        memberid += 1;
        return memberid.toString();
    }

    @Override
    public Member getMember(String memberId) {

        Member member = memberDao.getMemberById(memberId);

        System.out.println("查到的member " + member);

        // 数据库还有这个问题？？？
        member.setMemberid(member.getMemberid().trim());
        member.setMembername(member.getMembername().trim());
        member.setMemberemail(member.getMemberemail().trim());
        member.setMemberphone(member.getMemberphone().trim());

        return member;
    }

    @Override
    public List<Member> getAllMember() {
//        processEngineCore.
        return null;
    }
}
