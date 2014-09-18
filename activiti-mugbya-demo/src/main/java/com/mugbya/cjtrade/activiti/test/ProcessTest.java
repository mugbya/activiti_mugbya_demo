package com.mugbya.cjtrade.activiti.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-21.
 */
public class ProcessTest {
    public static void main(String[] args) {
        // 启动spring
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("spring-all.xml");

        ProcessTest t = new ProcessTest();
        // step 1 部署
//        t.deploymentInstance(applicationContext);
//        System.out.println("部署完------");
//
//        // step 2 启动流程实例
//        t.startInstance(applicationContext);
//        System.out.println("启动了--- ");
//
//        // step 3 查询用户任务
//        t.queryUserTask(applicationContext);
//        System.out.println("查询用户任务--- ");
//
//        // step 4 分配任何给user
//        t.claimTask(applicationContext);
//        System.out.println("任务分配--- ");

//        // step 5 查询个人任务列表
//        t.queryPersonalTaskList(applicationContext);
//        System.out.println("个人任务列表--- ");
//
//        // step 6 完成任务
//        t.completePersonalTask(applicationContext);
//        System.out.println("任务完成--- ");

        // step 7 查询历史流程信息
        t.queryHistoryProcessInstance(applicationContext);
        System.out.println("查看历史记录----");
    }


    // 部署流程实例
    public void deploymentInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得repositoryService
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        // 从文件部署流程
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("FinancialReportProcess.bpmn20.xml").deploy();
        System.out.println("部署的name  = "  + deployment.getName());
        System.out.println("部署的Id  = " + deployment.getId());
    }

    // 启动流程
    public void startInstance(ClassPathXmlApplicationContext applicationContext) {
        // 获得 runtimeservice对象
        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
        // 启动流程实例 ,注意这里的key是我们流程文件中的<process id="financialReport"
        // ,id属性,在Activiti术语叫key

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport","7777");
        System.out.println( "key =    "+processInstance.getBusinessKey());;
        System.out.println( "name =    "+processInstance.getName());;
        System.out.println( "key =    "+processInstance.getProcessInstanceId());;

    }

    // 查询组任务
    public void queryUserTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得 TaskService 对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        // 查询任务列表使用组名称
        List<Task> tasks02 = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        for (Task task : tasks02) {
            System.out.println(task.getName());
        }
    }


    // 分配任务给用户
    public void claimTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");
        // 得到组任务,也可以是 management组
        List<Task> tasks02 = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        // 任务分配之后这个任务就会从accountancy用户组的任务列表中消失
        for (Task task2 : tasks02) {
            taskService.claim(task2.getId(), "fozzie");
            System.out.println("任务名称:" + task2.getName());
        }

        // 此时查询fozzie人的个人任务列表
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task t : tasks) {
            System.out.println("已受理任务名称:" + t.getName());
        }
    }

    // 查询个人的任务列表
    public void queryPersonalTaskList(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");
        // 查询fozzie用户的个人任务列表
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        // 输出受理任务信息
        for (Task t : tasks) {
            System.out.println("已受理任务名称:" + t.getName());
        }
    }


    // 完成任务
    public void completePersonalTask(ClassPathXmlApplicationContext applicationContext) {
        // 获得TaskService对象
        TaskService taskService = (TaskService) applicationContext.getBean("taskService");

        // 查询 fozzie用户个人任务列表并完成其任务
        //List<Task> tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("financialReport").list();
        // 完成用户任务
        for (Task task : tasks) {
            System.out.println("完成任务名称:" + task.getName());
            // 通过任务id完成任务
            taskService.complete(task.getId());
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
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("financialReport").list();

        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            System.out.println("流程结束时间: " + historicProcessInstance.getEndTime());
            System.out.println(historicProcessInstance.getBusinessKey());
        }



    }
}
