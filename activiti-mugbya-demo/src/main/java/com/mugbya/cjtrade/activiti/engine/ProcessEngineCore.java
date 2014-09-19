package com.mugbya.cjtrade.activiti.engine;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-08-22.
 *          流程引擎核心类
 */
@Service("processEngineCore")
public class ProcessEngineCore {
    @Resource(name = "repositoryService")
    private RepositoryService repositoryService;

    @Resource(name = "runtimeService")
    private RuntimeService runtimeService;

    @Resource(name = "taskService")
    private TaskService taskService;

    @Resource(name = "historyService")
    private HistoryService historyService;


    /**
     * 部署流程
     */
    public void deploymentInstance() {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v1.bpmn20.xml").deploy();
    }

    /**
     * 增加业务
     *
     * @param processInstanceByKey
     * @param businessKey
     * @param variables
     * @return
     */
    public ProcessInstance startInstance(String processInstanceByKey, String businessKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey, businessKey, variables);
        System.out.println("业务ID是  =  [" + processInstance.getBusinessKey() + "]");
        return processInstance;
    }

    public List<Task> queryTaskList(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    }

    /**
     *
     * @param taskId 任务ID
     * @param variables 流程变量
     */
    public void handlerUserTask(String taskId, Map<String, Object> variables) {
        if (variables != null){
            taskService.complete(taskId, variables);
        }else{
            taskService.complete(taskId);
        }
    }

    /**
     * 获取流程变量信息
     *
     * @param taskId      任务Id
     * @param variableName 变量名
     * @return 变量的值
     */
    public String getVariable(String taskId, String variableName) {
        Object variableValue = taskService.getVariable(taskId, variableName);
        if (variableValue != null) {
            return variableValue.toString();
        }
        return null;
    }
    /**
     * 根据流程ID查看流程是否结束
     *
     * @param processInstanceId 流程ID
     */
    public boolean queryProcessIsEnd(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance != null && historicProcessInstance.getStartTime() != null && historicProcessInstance.getEndTime() != null) {
            return true;
        }
        return false;
    }

    /**
     * 得到所有的结束的进程实例
     */
    public List<HistoricProcessInstance> getAllProcessInstance() {
        return historyService.createHistoricProcessInstanceQuery().
                processDefinitionKey("activitiDemo_v3").finished().
                orderByProcessInstanceEndTime().desc().list();

    }

    /**
     * 得到流程实例
     *
     * @param processInstanceId
     * @return
     */
    public ProcessInstance queryProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
    }

}
