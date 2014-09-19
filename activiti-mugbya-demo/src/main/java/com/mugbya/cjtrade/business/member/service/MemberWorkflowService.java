package com.mugbya.cjtrade.business.member.service;

import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-09-19
 */
@Service("memberWorkflowService")
public class MemberWorkflowService {
    @Resource
    private ProcessEngineCore processEngineCore;

    public void startProcess(String processDefinitionKey, String businessKey, Map<String,Object> variables){
        processEngineCore.startInstance(processDefinitionKey , businessKey, variables);
    }

    public List<Task> taskList(String userId){
        /**
         * 后续还需加入分页
         */
        return  processEngineCore.queryTaskList(userId);
    }

    public void handlerTask(String taskId, String userId, Boolean value,String reason){
        Map <String ,Object> variables = new HashMap<>();
        variables.put(userId+"Pass",value);

        if (reason != null){
            variables.put(userId+"_reason",userId + " ： " + reason);
        }

        processEngineCore.handlerUserTask(taskId, variables);
    }

    public void revision(String taskId,  Boolean value){
        Map <String ,Object> variables = new HashMap<>();
        variables.put("reApply",value);

        processEngineCore.handlerUserTask(taskId, variables);
    }
}
