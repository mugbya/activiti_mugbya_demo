package com.mugbya.cjtrade.business.member.service;


import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.cjtrade.business.member.dao.MemberDao;
import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    //    @Resource
//    private MemberDao memberDao;
    @Resource
    private TaskService taskService;

    @Resource
    private ProcessEngineCore processEngineCore;

    private List<Member> memberList = new ArrayList<>();
    private List<UserTask> userTaskList = new ArrayList<>();

    public MemberServiceImpl() {
        Member member = new Member("009991", "什么", "0011", "898@7878.com", "122334234");
        memberList.add(member);
    }

    @Override
    public void save(Member member) {
//        member.setMemberid("00555");
//        memberDao.save(member);
        memberList.add(member);
    }

    @Override
    public List<UserTask> taskAll(Dto dto) {
//        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();
//
//        for (Task task : taskList) {
////            taskService.claim(task2.getId(), "mugbya");
////            System.out.println("任务名称:" + task2.getName());
//            UserTask userTask = new UserTask();
//            userTask.setID(task.getId());
//            userTask.setUsername("oo"); // 提交人呢
//            userTask.setAssignee("oo"); // 处理人
//            userTaskList.add(userTask);
//            System.out.println(task);
//        }

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("activitiDemo").list();
        for(Task task : taskList){
            UserTask userTask = new UserTask();
            System.out.println("任务的ID ========" + task.getId() + task.getId().getClass());
            userTask.setID(task.getId());
            userTask.setUsername("oo"); // 提交人呢
            userTask.setAssignee("未设置"); // 处理人
            userTaskList.add(userTask);
            System.out.println(task);
        }
        return userTaskList;
    }

    @Override
    public void handlerUserTask(String taskId) {

//        processEngineCore.handlerUserTask(taskId,"oo");

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("activitiDemo").list();
        System.out.println("任务列表----- " + taskList.size());
//        taskService.complete(taskList.get(0).getId());

        taskService.complete(taskId);

        UserTask userTask = new UserTask();
        userTask.setID(taskId);
        System.out.println(userTask);
        memberList.remove(userTask);
        System.out.println(memberList.size());

    }

    //    @Override
//    public Member getMemberById(String memberid) {
//        return memberDao.getMemberById(memberid);
//    }
//
//    @Override
//    public Member getMemberByName(String membername) {
//        return memberDao.getMemberByName(membername);
//    }
//
//    @Override
//    public List<Member> getAll(Dto dto) {
//        return memberDao.getAll(dto);
//    }
//
//    @Override
//    @Transactional()
//    public void delete(String memberid) {
//        memberDao.delete(memberid);
//    }
//
//    @Override
//    @Transactional()
//    public void update(Member member) {
//        memberDao.update(member);
//    }
//
//    @Override
//    public boolean delRoleAndPermission(String memberid) {
//        memberDao.delRole(memberid);
//        memberDao.delPermission(memberid);
//        return true;
//    }
}
