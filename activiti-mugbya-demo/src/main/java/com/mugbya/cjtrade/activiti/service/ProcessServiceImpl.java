package com.mugbya.cjtrade.activiti.service;

import com.mugbya.cjtrade.activiti.engine.ProcessEngineCore;
import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.core.collection.Dto;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-22.
 */
@Service("processService")
public class ProcessServiceImpl implements ProcessService {

    List<UserTask> userTaskList = new ArrayList<>();

    @Resource()
    private ProcessEngineCore processEngineCore;

    public ProcessServiceImpl() {
        
    }

    @Override
    public void startProcess(String key) {
        processEngineCore.startInstance(key);
    }

    @Override
    public List<UserTask> taskList(Dto dto) {

        /**
         * 这里还是查询所有的任务，没有用当前用户来查询旗下的任务
         */
        List<Task> tasks = processEngineCore.taskList("activitiDemo_v2");

        for (Task task : tasks){
            UserTask userTask = new UserTask(task.getId(), "oo", task.getAssignee());
            userTaskList.add(userTask);
        }

        return userTaskList;
    }

    @Override
    public void haldlerTask(String taskId, String userId) {

        /**
         * 处理任务,因为将任务进行封装，所以在处理完，还需手动去移除任务
         */
        processEngineCore.handlerUserTask(taskId, userId);


        /**
         * 不要用迭代器来删除集合里面的元素
         */
        for(int i=0; i<userTaskList.size(); i++){
            if (userTaskList.get(i).getID().equals(taskId)){
                 userTaskList.remove(userTaskList.get(i));
            }
        }

    }


}
