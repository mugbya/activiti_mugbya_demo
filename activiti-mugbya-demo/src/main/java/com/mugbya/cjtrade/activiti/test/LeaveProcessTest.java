package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-08-22.
 *
 * 请假流程测试
 */
public class LeaveProcessTest {


    /**************** 日志对象 ****************/
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LeaveProcessTest.class);

    public static void main(String[] arg0) {

        // 启动spring
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-all.xml");

        LeaveProcessTest leaveProcessTest = new LeaveProcessTest();

        // step1 部署流程
        leaveProcessTest.deploymentInstance(applicationContext);
        System.out.println("部署------");

        // step2 启动流程实例
        leaveProcessTest.startInstance(applicationContext);
        System.out.println("启动流程实例--------");

        // step3 查询用户任务
        leaveProcessTest.queryUserTask(applicationContext);
        System.out.println("查询用户任务----");

        // 查询当前流程历史任务
        leaveProcessTest.queryHistoryProcessInstance(applicationContext);
        System.out.println("历史------");

    }

    // 部署流程实例
    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得repositoryService
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        // 从文件部署流程
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("qingjiaProcess.bpmn20.xml").deploy();

    }

    // 启动流程实例，并且完成当前流程
    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得 runtimeservice对象
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
        // 启动流程实例 ,注意这里的key是我们流程文件中的<process id="myProcess",id属性,在Activiti术语叫key
        // yangjie(用户) 启动一个请假流程实例
        Map<String, Object> proMap = new HashMap<String, Object>();
        proMap.put("loginUser", "qq");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", proMap);

        // 获得 TaskService 对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");
        // 查询当前用户任务列表
        List<Task> tasks2 = taskService.createTaskQuery().taskAssignee("qq").list();
        for (Task task : tasks2) {
//            System.out.println("task name = " + task.getName());
//            taskService.complete(task.getId());
            System.out.print("task.getAssignee() = " + task.getAssignee() + " &&  task.getName() = ");
            System.out.println(task.getName());
        }
    }

    // 查询当前用户任务列表 并且完成所有代办任务
    public void queryUserTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得 TaskService 对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        // 查询当前用户任务列表
        List<Task> tasks1 = taskService.createTaskQuery().taskAssignee("sa").list();
        for (Task task : tasks1) {
            logger.debug("task name = " + task.getName());
            System.out.println("task name = " + task.getName());
            Map<String, Object> proMap = new HashMap<String, Object>();
            proMap.put("action", "2");
            taskService.complete(task.getId(), proMap);
        }

        // 查询当前用户任务列表
        List<Task> tasks2 = taskService.createTaskQuery().taskAssignee("admin").list();
        for (Task task : tasks2) {
            logger.debug("task name = " + task.getName());
            System.out.println("task name = " + task.getName());
            Map<String, Object> proMap = new HashMap<String, Object>();
            proMap.put("action", "2");
            taskService.complete(task.getId(), proMap);
        }
    }

    // 查询历史流程信息,也许你在查询的时候这个任务没有结束
    // 那么请你将management组的任务claimTask分配给用户然后completePersonalTask完成任务
    // 这个流程实例就算完成了,你在这里也才会查询出来,否则流程实例没有到达
    public void queryHistoryProcessInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获取historyService
        HistoryService historyService = (HistoryService) applicationContext.getBean("historyService");
        // 在这里需要注意的是,你的financialReport流程如果启动多个,singleResult将会出错
        // 由于这里是测试我很清除这个实例只启动了一个,所以使用singleResult方法,如果你在测试时候需要注意
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("myProcess").list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            logger.debug("流程结束时间: " + historicProcessInstance.getEndTime());
            System.out.println("流程结束时间: " + historicProcessInstance.getEndTime());
        }
    }

}
