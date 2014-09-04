package com.mugbya.cjtrade.business.member.model;

import java.io.Serializable;

/**
 * @author mugbya
 * @version 2014-07-22.
 */
public class Member implements Serializable{
    private String memberid;
    private String membername;
    private String deptid;
    private String memberemail;
    private String memberphone;

    public Member() {
    }


    public Member(String memberid, String membername, String deptid, String memberemail, String memberphone) {
        this.memberid = memberid;
        this.membername = membername;
        this.deptid = deptid;
        this.memberemail = memberemail;
        this.memberphone = memberphone;
    }

    public String getMemberphone() {
        return memberphone;
    }

    public void setMemberphone(String memberphone) {
        this.memberphone = memberphone;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getMemberemail() {
        return memberemail;
    }

    public void setMemberemail(String memberemail) {
        this.memberemail = memberemail;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberid='" + memberid + '\'' +
                ", membername='" + membername + '\'' +
                ", deptid='" + deptid + '\'' +
                ", memberemail='" + memberemail + '\'' +
                ", memberphone='" + memberphone + '\'' +
                '}';
    }
}
