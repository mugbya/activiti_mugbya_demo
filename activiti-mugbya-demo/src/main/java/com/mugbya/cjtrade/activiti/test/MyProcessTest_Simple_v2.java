package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-09-04
 *
 * 在流程定义中指定用户进行处理任务
 */
public class MyProcessTest_Simple_v2 {

    public static void main(String[] args) {
        // 启动spring
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        MyProcessTest_Simple_v2 t = new MyProcessTest_Simple_v2();

        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");

        // step 2 启动流程实例
//        t.startInstance(applicationContext);
//        System.out.println("启动了--- ");

        // step 3 查看用户任务
        t.queryUserTask(applicationContext);
        System.out.println("查看用户任务");

        // 完成任务
        t.completePersonalTask(applicationContext);
        System.out.println("任务完成--- ");

    }

    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得repositoryService
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        // 从文件部署流程
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v2.bpmn20.xml").deploy();
        System.out.println("部署的Id  = " + deployment.getId());
    }

    public void startInstance(ClassPathXmlApplicationContext applicationContext){
        // 获得 runtimeservice对象
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activitiDemo_v2");

        System.out.println( "key =    "+processInstance.getBusinessKey());
        System.out.println( "进程实例ID =    "+processInstance.getProcessInstanceId());
    }

    public void queryUserTask(ClassPathXmlApplicationContext applicationContext){
        // 获得 TaskService 对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        // 查询被指定任务列表
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("mugbya").list();

        for(Task task : tasks){
            System.out.println("task id = " +task.getId() + "  task name = " + task.getName());
        }
    }

    /**
     * 处理任务
     * @param applicationContext
     */
    public void completePersonalTask(ClassPathXmlApplicationContext applicationContext){
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();

        for (Task task : taskList){
            taskService.complete(task.getId());
        }
    }
}
