package com.mugbya.cjtrade.activiti.controller;

import com.mugbya.cjtrade.activiti.service.ProcessService;
import com.mugbya.core.collection.BaseDto;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.common.CommonController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mugbya
 * @version 2014-09-03
 */
@RestController
public class ProcessController extends CommonController{

    @Resource
    private ProcessService processService;


    @RequestMapping(value = "process/start.json")
    public void startProcess(){
        processService.startProcess("activitiDemo_v2");
    }

    @RequestMapping(value = "process/tasklist.json")
    public Object tasklist(){
        Dto params = new BaseDto();

        return success(processService.taskList(params));

    }

    @RequestMapping(value = "process/handler.json")
    public void handlerTask(String taskId, String userId){
        processService.haldlerTask(taskId,userId);
    }

}
