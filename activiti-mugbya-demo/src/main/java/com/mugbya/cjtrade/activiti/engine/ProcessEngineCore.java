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
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-08-22.
 *  流程引擎核心类
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
     * 流程启动
     * @param processInstanceByKey
     * @return
     */
    public ProcessInstance startInstance(String processInstanceByKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey);
        System.out.println("process start success  key [" + processInstance.getId() + "]" );
        return processInstance;
    }

    /**
     * 流程启动 增加流程变量
     * @param processInstanceByKey
     * @param variables
     * @return
     */
    public ProcessInstance startInstance(String processInstanceByKey, Map<String,Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey,variables);
        System.out.println("process start success  key [" + processInstance.getId() + "]" );
        return processInstance;
    }

    /**
     * 增加业务
     * @param processInstanceByKey
     * @param businessKey
     * @param variables
     * @return
     */
    public ProcessInstance startInstance(String processInstanceByKey,  String businessKey, Map<String,Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey, businessKey, variables);
        System.out.println("业务ID是  =  [" + processInstance.getBusinessKey() + "]" );
        return processInstance;
    }

    /**
     * 得到流程实例
     * @param processInstanceId
     * @return
     */
    public ProcessInstance queryProcessInstance(String processInstanceId){
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
    }

    /**
     * 获取流程申请者
     * @param taskId
     * @return
     */
    public Object getApplyUser(String  taskId){
        return  taskService.getVariable(taskId,"applyUser");      
    }

    /**
     * 同名（key）下的所有任务
     * @param key
     * @return
     */
    public List<Task> taskList (String key){
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).list();
        return  taskList;
    }

    /**
     * 查询指定人的用户列表
     * @param userName
     * @return
     */
    public List<Task> queryUserTaskList(String userName) {
        // 查询当前用户任务列表
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userName).list();
        System.out.println("当前用户的任务个数" + taskList.size());
        return taskList;
    }

    public List<Task> queryTaskList(String userId){
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    }

    /**
     * 处理任务
     *
     * @param taskId
     * @param userId
     */
    public void handlerUserTask(String taskId, String userId) {

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();

        for (Task task : tasks) {
            if (taskId.equals(task.getId())){
                taskService.complete(task.getId());
            }
        }

    }

    /**
     * web用来处理任务
     * @param taskId
     */
    public void handlerTask (String taskId){
        taskService.complete(taskId);
    }

    /**
     * 完成UserTask
     *
     * @param taskId 任务ID
     * @param proMap 流程变量
     */
    public void completeUserTask(String taskId, Map<String, Object> proMap) {
        if (proMap != null) {
            taskService.complete(taskId, proMap);
        } else {
            taskService.complete(taskId);
        }
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

//    /**
//     * 得到所有的启动的进程实例
//     */
//    public ProcessInstance getAllProcessInstance(){
//
//    }


}
