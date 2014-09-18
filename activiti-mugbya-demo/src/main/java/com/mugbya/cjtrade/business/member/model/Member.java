package com.mugbya.cjtrade.business.member.model;

import org.activiti.engine.task.Task;

import java.io.Serializable;

/**
 * @author mugbya
 * @version 2014-07-22.
 *
 * 除了含有基本的业务信息外，还要含一些流程信息
 *
 */
public class Member implements Serializable{
    private String memberid;
    private String membername;
    private String deptid;
    private String memberemail;
    private String memberphone;

    //临时属性,不存数据库
    private String applyUser;
    private Task task;
    private String assignee;
    private String taskId;
    private String taskName;
    private String reason;

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

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberid='" + memberid + '\'' +
                ", membername='" + membername + '\'' +
                ", deptid='" + deptid + '\'' +
                ", memberemail='" + memberemail + '\'' +
                ", memberphone='" + memberphone + '\'' +
                ", applyUser='" + applyUser + '\'' +
                ", task=" + task +
                ", assignee='" + assignee + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
