package com.mugbya.cjtrade.business.user.controller;

import com.mugbya.cjtrade.business.user.model.User;
import com.mugbya.cjtrade.business.user.service.UserService;
import com.mugbya.core.collection.BaseDto;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.common.CommonController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-21.
 */
@RestController
public class UserController extends CommonController {

    @Resource
    private transient UserService userService;

    @RequestMapping(value = "/user/login.json")
    public String loginAccount(String username, String userpassword, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = userService.checkUser(username, userpassword);
            if (user == null) {
                return "false";
            }
            request.getSession().setAttribute("loginuser", user);
            return "true";
        } catch (Exception e) {
            System.out.println(e);
        }
        return "false";
    }

    @RequestMapping(value = "/user/all.json")
    public Object getEmployees() {
        Dto params = new BaseDto();
        List<User> employees = userService.getUser(params);
        return success(employees);
    }

}
