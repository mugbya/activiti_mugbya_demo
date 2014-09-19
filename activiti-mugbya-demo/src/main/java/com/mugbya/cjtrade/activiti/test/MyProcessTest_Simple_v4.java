package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-09-16
 *
 * 实现驳回功能
 */
public class MyProcessTest_Simple_v4 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        MyProcessTest_Simple_v4 t = new MyProcessTest_Simple_v4();

        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");

//        // step 2 启动流程实例
//        t.startInstance(applicationContext);
//        System.out.println("启动了--- ");

//        // step 3 查看该流程下的所有任务列表
//        t.taskAll(applicationContext);
//
        // step 4 以mugbya账户处理任务
        t.completePersonalTaskbyMugbya(applicationContext);
        System.out.println("任务完成--- ");
//
//        // step 5 查看该流程下的所有任务列表
//        t.taskAll(applicationContext);
//
        // step 6 以yeats账户处理任务
        t.completePersonalTaskbyYeats(applicationContext);
        System.out.println("任务完成--- ");
//
//        t.taskAll(applicationContext);

        // 处理調整申请
        t.modifyApply(applicationContext);

        //得到审批成功的会员信息
        // 得到所有的结束的流程
//        t.getFinishedAllProcessInstance(applicationContext);
//        System.out.println("结束的流程");

    }

    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v4.bpmn20.xml").deploy();
        System.out.println("部署的Id  = " + deployment.getId());
    }

    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
        IdentityService identityService = (IdentityService) applicationContext.getBean("identityService");

        StrongUuidGenerator uuidGenerator = (StrongUuidGenerator)applicationContext.getBean("uuidGenerator");
        String busid = uuidGenerator.getNextId();

//        Map<String ,Object > variables = new HashMap<>();
//        variables.put("applyUser","mugbya");
        identityService.setAuthenticatedUserId("mugbya");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activitiDemo_v4",busid);

        System.out.println("业务key =    " + processInstance.getBusinessKey());
        System.out.println("进程实例ID =    " + processInstance.getProcessInstanceId());
    }

    public void taskAll(ClassPathXmlApplicationContext applicationContext){
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("activitiDemo_v4").list();

        for(Task task : taskList){
            System.out.println("任务列表 ： " + task);
        }
    }

    public void completePersonalTaskbyMugbya(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();

        Map<String ,Object> variables = new HashMap<>();
//        variables.put("mugbyaPass", true);
        variables.put("mugbyaPass", false);

        for (Task task : taskList) {
            taskService.complete(task.getId(),variables);
        }
    }

    public void completePersonalTaskbyYeats(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("yeats").list();

        Map<String ,Object> variables = new HashMap<>();
        variables.put("reApply", false);

        for (Task task : taskList) {
            taskService.complete(task.getId(),variables);
        }
    }

    public void modifyApply(ClassPathXmlApplicationContext applicationContext){
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();

        Map<String ,Object> variables = new HashMap<>();
        variables.put("reApply", false);

        for (Task task : taskList) {
            taskService.complete(task.getId(),variables);
        }
    }

    public void getFinishedAllProcessInstance(ClassPathXmlApplicationContext applicationContext){
        HistoryService historyService = (HistoryService) applicationContext.getBean("historyService");
        List<HistoricProcessInstance> query =
                historyService.createHistoricProcessInstanceQuery().processDefinitionKey("activitiDemo_v4").
                        finished().orderByProcessInstanceEndTime().desc().list();

        for (HistoricProcessInstance oo : query){
            System.out.println(oo.getBusinessKey());
        }
    }

}
