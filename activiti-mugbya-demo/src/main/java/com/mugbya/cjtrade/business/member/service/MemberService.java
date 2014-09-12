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

    /**
     * 返回给定id的member
     * @param memberId
     * @return
     */
    Member getMember(String memberId);

    List<Member> getAllMember();

    List<Member> UsertaskList(Dto dto, HttpServletRequest request);

    void handlerTask(String taskId, String userId);

}
