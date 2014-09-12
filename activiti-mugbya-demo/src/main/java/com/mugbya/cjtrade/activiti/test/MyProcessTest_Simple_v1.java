package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-25.
 */
public class MyProcessTest_Simple_v1 {

    public static void main(String[] args) {
        // 启动spring
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        MyProcessTest_Simple_v1 t = new MyProcessTest_Simple_v1();

        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");

        //step 2 启动流程实例
        t.startInstance(applicationContext);
        System.out.println("启动了--- ");

        // 完成任务
//        t.completePersonalTask(applicationContext);
//        System.out.println("任务完成--- ");

    }


    // 部署流程实例
    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得repositoryService
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        // 从文件部署流程
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v1.bpmn20.xml").deploy();
        System.out.println("部署的Id  = " + deployment.getId());


    }

    // 启动流程
    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得 runtimeservice对象
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activitiDemo_v1");

        System.out.println( "key =    "+processInstance.getBusinessKey());
        System.out.println( "进程实例ID =    "+processInstance.getProcessInstanceId());
    }


    // 完成任务
    public void completePersonalTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("activitiDemo_v1").list();
        System.out.println("任务列表----- " + taskList.size());
        taskService.complete(taskList.get(0).getId());
    }

}
