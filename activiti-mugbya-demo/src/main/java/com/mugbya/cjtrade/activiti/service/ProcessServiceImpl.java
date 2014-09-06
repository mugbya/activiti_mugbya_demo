package com.mugbya.cjtrade.activiti.service;

import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.utils.WebUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author mugbya
 * @version 2014-08-22.
 */
@Service("processService")
public class ProcessServiceImpl implements ProcessService {


    @Resource()
    private ProcessEngineCore processEngineCore;

    @Resource
    private IdentityService identityService;

    @Resource
    private HistoryService historyService;

    public ProcessServiceImpl() {

    }

    /**
     * 启动流程加上提交人
     *
     * @param key
     */
    @Override
    public void startProcess(String key, HttpServletRequest request) {
        String applicant = WebUtil.getLoginUser(request).getUsername();

        // 设置申请人
        Map<String ,Object> variables = new HashMap<>();
        variables.put("applyUser",applicant);

        processEngineCore.startInstance(key, variables);
    }

    @Override
    public List<UserTask> taskList(Dto dto) {
        List<UserTask> userTaskList = new ArrayList<>();
        /**
         * 这里还是查询所有的任务，没有用当前用户来查询旗下的任务
         */
        List<Task> tasks = processEngineCore.taskList("activitiDemo_v2");

        for (Task task : tasks) {
            UserTask userTask = new UserTask(task.getId(), "oo", task.getAssignee());
            userTaskList.add(userTask);
        }

        return userTaskList;
    }

    /**
     * 查询当前登陆用户的任务列表
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    public List<UserTask> UsertaskList(Dto dto, HttpServletRequest request) {

        // 注意这里的用局部变量
        List<UserTask> userTaskList = new ArrayList<>();

        String loginUser = WebUtil.getLoginUser(request).getUsername();
        System.out.println("查询任务前，先看是谁 " + loginUser);
        List<Task> tasks = processEngineCore.queryUserTaskList(loginUser);

        System.out.println("------------------------------------------");

        List  oo =  historyService.createHistoricProcessInstanceQuery().startedBy("oo").list();

        System.out.println("oo 的长度  是 "+ oo.size());
        System.out.println("输出完毕------------");

        for (Task task : tasks) {
            String applyUser = processEngineCore.getApplyUser(task.getId()).toString();
            UserTask userTask = new UserTask(task.getId(), applyUser, task.getAssignee());
            userTaskList.add(userTask);
        }
        return userTaskList;

    }


    @Override
    public void haldlerTask(String taskId, String userId) {
        processEngineCore.handlerUserTask(taskId, userId);
    }
}
