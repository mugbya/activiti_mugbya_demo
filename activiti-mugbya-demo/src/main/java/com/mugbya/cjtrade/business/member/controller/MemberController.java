package com.mugbya.cjtrade.business.member.controller;


import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.cjtrade.business.member.service.MemberService;
import com.mugbya.cjtrade.business.user.model.User;
import com.mugbya.core.collection.BaseDto;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.common.CommonController;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author mugbya
 * @version 2014-07-22.
 */
@RestController
public class MemberController extends CommonController {
    @Resource
    private MemberService memberService;

    @Resource
    private ProcessEngineCore processEngineCore;




    @RequestMapping(value = "/member/add.json")
    public void addMember(Member member) {

        member.setMemberid("8888");
        System.out.println("member" + member);
        memberService.save(member);

//        //部署流程
//        processEngineCore.deploymentInstance();

//        String businessKey = member.getMemberid();
//        System.out.println("businessKey = " + businessKey);
//
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("loginUser", "mugbya");
//        processEngineCore.startInstance("activitiDemo",businessKey,variables);

        processEngineCore.startInstance("activitiDemo");

        System.out.println("流程启动 --- ");

    }

    // 任务列表
    @RequestMapping(value = "/task/list.json")
    public Object tasklist(){
        Dto params = new BaseDto();

       List<UserTask> taskList = memberService.taskAll(params);
        return success(taskList);
    }


    @RequestMapping(value = "/task/hanlder.json")
    public void taskHandler(String taskId){
        System.out.println("任务处理 的ID-------- " + taskId);
        memberService.handlerUserTask(taskId);

        System.out.println("任务完成 --------- ");
    }
//
//    @RequestMapping(value = "/member/delete.json")
//    public void delete(String[] memberids) {
//        for (int i = 0; i < memberids.length; i++) {
//            System.out.println(memberids[i]);
//            memberService.delete(memberids[i]);
//        }
//        //memberService.delete(memberid);
//    }
//
//    @RequestMapping(value = "/member/update")
//    public void update(Member member) {
//        memberService.update(member);
//    }
//


}
