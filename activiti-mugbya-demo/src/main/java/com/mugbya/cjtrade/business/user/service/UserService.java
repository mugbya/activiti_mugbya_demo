package com.mugbya.cjtrade.business.user.service;

import com.mugbya.cjtrade.business.user.model.User;
import com.mugbya.core.collection.Dto;

import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-21.
 */
public interface UserService {

    User getUserById(String id);

    List<User> getUser(Dto dto);

    User checkUser(String username, String userpassword);

}
