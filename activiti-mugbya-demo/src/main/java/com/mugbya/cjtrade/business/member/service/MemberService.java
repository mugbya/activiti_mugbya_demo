package com.mugbya.cjtrade.business.member.service;


import com.mugbya.cjtrade.activiti.entity.UserTask;
import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
public interface MemberService {
    void save(Member member);

//    Member getMemberById(String memberid);
//
//    Member getMemberByName(String membername);
//
//    List<Member> getAll(Dto dto);

    // 获取所有任务，不因该写在这里
    List<UserTask> taskAll(Dto dto);

    //处理任务
    void handlerUserTask(String taskId);
}
