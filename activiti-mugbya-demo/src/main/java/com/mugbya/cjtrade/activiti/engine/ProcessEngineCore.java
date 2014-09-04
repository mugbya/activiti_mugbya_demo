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
 *          <p/>
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
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo.bpmn20.xml").deploy();
        System.out.println("process deployment success & processID is " + deployment.getId() +
                ",createTime is " + deployment.getDeploymentTime());
    }

    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }


    /**
     * 根据任务ID获得任务实例
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    private TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

    /**
     * 启动流程实例
     *
     * @param processInstanceByKey 流程部署KEY
     * @param proMap               流程变量
     */
    public ProcessInstance startInstance(String processInstanceByKey, String businessKey, Map<String, Object> proMap) {
        ProcessInstance processInstance = null;
        if (proMap != null) {
            processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey, businessKey, proMap);
        } else {
            processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey);
        }
        System.out.println("process start success  key [" + processInstance.getBusinessKey() + "]" + processInstance.getId());
        return processInstance;
    }

    /**
     * 重载流程启动
     *
     * @param processInstanceByKey
     * @return
     */
    public ProcessInstance startInstance(String processInstanceByKey) {
        ProcessInstance processInstance = null;

        processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey);

        System.out.println("process start success  key [" + processInstance.getId() + "]" );
        return processInstance;
    }


    /**
     * 根据任务ID获取对应的流程实例
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(findTaskById(taskId).getProcessInstanceId()).singleResult();
        if (processInstance == null) {
            throw new Exception("流程实例未找到!");
        }
        return processInstance;
    }


    /**
     * 根据executionId查询task
     *
     * @param executionId
     * @return
     */
    public List<Task> queryByExecutionId(String executionId) {
        List<Task> taskList = taskService.createTaskQuery().executionId(executionId).list();
        return taskList;
    }

    /**
     * 回去同名（key）下的所有任务
     * @param key
     * @return
     */
    public List<Task> taskList (String key){
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).list();
        return  taskList;
    }

    /**
     * 根据executionId查询task
     *
     * @param executionId
     * @return
     */
    public Task queryByExecutionIdSingle(String executionId) {
        Task task = taskService.createTaskQuery().executionId(executionId).singleResult();
        return task;
    }


    /**
     * 查询UserTask列表
     *
     * @param userName
     * @return
     */
    public List<Task> queryUserTaskList(String userName) {
        // 查询当前用户任务列表
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userName).list();
        return taskList;
    }


    /**
     * 处理任务
     *
     * @param taskId
     * @param userId
     */
    public void handlerUserTask(String taskId, String userId) {
//        taskService.claim(taskId,userId);
//        taskService.complete(taskId);

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("oo").list();

        for (Task task : tasks) {
            taskService.complete(task.getId());
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
}
