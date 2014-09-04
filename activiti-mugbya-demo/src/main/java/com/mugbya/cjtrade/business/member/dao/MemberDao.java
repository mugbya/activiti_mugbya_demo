package com.mugbya.cjtrade.business.member.dao;



import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.core.collection.Dto;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
public interface MemberDao {
    void save(Member member);

    Member getMemberById(String memberid);

    Member getMemberByName(String membername);

    List<Member> getAll(Dto dto);

    void delete(String memberid);

    void update(Member member);

    void delRole(String memberid);

    void delPermission(String memberid);
}
