package com.mugbya.cjtrade.business.member.service;


import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
public interface MemberService {

    void start(Member member,Map<String,Object> variables);

    void saveMember(Member member);

    Member getMember(String memberId);

    List<Member> getAllMember();

    List<Member> UsertaskList(Dto dto, HttpServletRequest request);

    void handlerTask(String taskId, String userId, Boolean variables, String reason);

    void revision(Member member , String taskId,  Boolean variables);
}
