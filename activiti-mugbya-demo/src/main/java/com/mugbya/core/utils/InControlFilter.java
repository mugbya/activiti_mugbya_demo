package com.mugbya.core.utils;

import com.mugbya.cjtrade.business.user.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mugbya
 * @version 2014-08-21.
 */
public class InControlFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器--->执行顺序 1---->preHandle方法");
        System.out.println(request.getRequestURL().toString());
        User user = WebUtil.getLoginUser(request);
        if (user !=null){
            //已经登陆
            request.getRequestDispatcher("/WEB-INF/page/main.jsp").forward(request,response);
        }else {
            //返回登陆页面
            response.sendRedirect("/login.jsp");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("拦截器--->执行顺序 2---->postHandle方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
//        System.out.println("拦截器--->执行顺序 3---->afterCompletion方法");
    }

}
