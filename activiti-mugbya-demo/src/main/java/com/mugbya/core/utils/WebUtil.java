package com.mugbya.core.utils;

import com.mugbya.cjtrade.business.user.model.User;

import javax.servlet.http.HttpServletRequest;


/**
 * @author mugbya
 * @version 2014-08-21.
 */
public class WebUtil {

    //拿到登陆用户
    public static User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loginuser");
    }

    //删除session
    public static void destorySession(HttpServletRequest request){
        request.getSession().removeAttribute("loginUser");
    }

}
