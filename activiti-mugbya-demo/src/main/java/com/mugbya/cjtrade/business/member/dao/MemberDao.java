package com.mugbya.cjtrade.business.member.dao;

import com.mugbya.cjtrade.business.member.model.Member;

/**
 * @author mugbya
 * @version 2014-09-10
 */
public interface MemberDao {

    void save(Member member);

    Member getMemberById(String memberid);

}
