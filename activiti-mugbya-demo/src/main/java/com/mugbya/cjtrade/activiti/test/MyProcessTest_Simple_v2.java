package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-09-04
 *          <p/>
 *          在流程定义中指定用户进行处理任务
 *          <p/>
 *          在启动流程时指定发起人
 */
public class MyProcessTest_Simple_v2 {

    public static void main(String[] args) {
        // 启动spring
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        MyProcessTest_Simple_v2 t = new MyProcessTest_Simple_v2();

        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");
//
        // step 2 启动流程实例
        t.startInstance(applicationContext);
        System.out.println("启动了--- ");

        // step 3 查看用户任务
        t.queryUserTask(applicationContext);
        System.out.println("查看用户任务");

        // 完成任务
        t.completePersonalTask(applicationContext);
        System.out.println("任务完成--- ");

        t.queryUserTask(applicationContext);
        System.out.println("查看用户任务");

    }

    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得repositoryService
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        // 从文件部署流程
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("activitiDemo_v2.bpmn20.xml").deploy();
        System.out.println("部署的Id  = " + deployment.getId());
    }

    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得 runtimeservice对象
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");

//        // 保存启动人信息方法之一
//        IdentityService identityService = (IdentityService) applicationContext.getBean("identityService");
//        identityService.setAuthenticatedUserId("mm");

        // 保存启动人信息方法之二,将之保存在流程变量中
        Map<String ,Object > variables = new HashMap<>();
        variables.put("applyUser","kk");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activitiDemo_v2",variables);

        System.out.println("key =    " + processInstance.getBusinessKey());
        System.out.println("进程实例ID =    " + processInstance.getProcessInstanceId());
    }

    public void queryUserTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得 TaskService 对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        // 查询被指定任务列表
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("mugbya").list();

        for (Task task : tasks) {
            System.out.println(task);
//            Map<String,Object> vars = taskService.getVariables(task.getId());
//            System.out.println("申请人是:" + vars.get( "applyUser"));
//            for (String variableName : vars.keySet()) {
//                String val = (String) vars.get(variableName);
//                System.out.println(variableName + " = " +val);

            System.out.println("申请人是:" + taskService.getVariable(task.getId(),"applyUser").toString());

//            }
        }
    }

    /**
     * 处理任务
     *
     * @param applicationContext
     */
    public void completePersonalTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("mugbya").list();

        for (Task task : taskList) {
            taskService.complete(task.getId());
        }
    }
}
