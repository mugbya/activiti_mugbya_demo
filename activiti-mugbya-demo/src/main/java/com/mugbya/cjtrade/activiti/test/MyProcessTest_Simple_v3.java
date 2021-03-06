package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
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
 * @version 2014-09-08
 *
 *  本节测试新流程定义，增加一个简单的节点
 */
public class MyProcessTest_Simple_v3 {
    public static void main(String[] args) {
        // 启动spring
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        MyProcessTest_Simple_v3 t = new MyProcessTest_Simple_v3();

//        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");

        // step 2 启动流程实例
        t.startInstance(applicationContext);
        System.out.println("启动了--- ");

//        // step 3 查看该流程下的所有任务列表
//        t.taskAll(applicationContext);
//
//        // step 4 以mugbya账户处理任务
//        t.completePersonalTaskbyMugbya(applicationContext);
//        System.out.println("任务完成--- ");
//
//        // step 5 查看该流程下的所有任务列表
//        t.taskAll(applicationContext);
//
//        // step 6 以yeats账户处理任务
//        t.completePersonalTaskbyYeats(applicationContext);
//        System.out.println("任务完成--- ");

//        // 得到运行中的流程
//        t.getActiveAllProcessInstance(applicationContext);
//        System.out.println("运行中的流程");
//
//        // 得到所有的结束的流程
//        t.getFinishedAllProcessInstance(applicationContext);
//        System.out.println("结束的流程");
    }

    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v3.bpmn20.xml").deploy();
        System.out.println("部署的Id  = " + deployment.getId());
    }

    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
        StrongUuidGenerator uuidGenerator = (StrongUuidGenerator)applicationContext.getBean("uuidGenerator");
        String busid = uuidGenerator.getNextId();

        System.out.println(busid);

        //668b4c7d-3b43-11e4-bd10-446d57682e60
        //729ecfcb-3b45-11e4-b4ab-446d57682e60
        //d05f5c13-3b45-11e4-8654-446d57682e60

//        StrongUuidGenerator
        //System.out.println(object + " -- " + object.getClass());

//        Map<String ,Object > variables = new HashMap<>();
//        variables.put("applyUser","mugbya");
//
//        String busid =
//
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activitiDemo_v3",busid,variables);
//
//        System.out.println("key =    " + processInstance.getBusinessKey());
//        System.out.println("进程实例ID =    " + processInstance.getProcessInstanceId());
    }

    public void taskAll(ClassPathXmlApplicationContext applicationContext){
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("activitiDemo_v3").list();

        for(Task task : taskList){
            System.out.println("任务列表 ： " + task);
        }
    }

    public void completePersonalTaskbyMugbya(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();

        for (Task task : taskList) {
            taskService.complete(task.getId());
        }
    }

    public void completePersonalTaskbyYeats(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("yeats").list();

        for (Task task : taskList) {
            taskService.complete(task.getId());
        }
    }

    public void getActiveAllProcessInstance(ClassPathXmlApplicationContext applicationContext){
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
        List<ProcessInstance> historicProcessInstances =
                runtimeService.createProcessInstanceQuery().processDefinitionKey("activitiDemo_v3").active().orderByProcessInstanceId().desc().list();

        for (ProcessInstance oo : historicProcessInstances){
            System.out.println(oo);
            System.out.println("业务ID " + oo.getBusinessKey());
        }
    }

    public void getFinishedAllProcessInstance(ClassPathXmlApplicationContext applicationContext){
        HistoryService historyService = (HistoryService) applicationContext.getBean("historyService");
        List<HistoricProcessInstance> query =
                historyService.createHistoricProcessInstanceQuery().processDefinitionKey("activitiDemo_v3").
                        finished().orderByProcessInstanceEndTime().desc().list();

        for (HistoricProcessInstance oo : query){
            System.out.println(oo.getBusinessKey());
        }
    }
}
