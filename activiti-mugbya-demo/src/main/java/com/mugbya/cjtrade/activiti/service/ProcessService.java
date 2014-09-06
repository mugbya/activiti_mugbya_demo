package com.mugbya.cjtrade.activiti.service;

import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.core.collection.Dto;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-22.
 *
 * 工作流引擎服务
 */
public interface ProcessService {

    void startProcess(String key,HttpServletRequest request);

    List<UserTask> taskList(Dto dto);

    List<UserTask> UsertaskList(Dto dto, HttpServletRequest request);

    void haldlerTask(String taskId, String userId);

}
