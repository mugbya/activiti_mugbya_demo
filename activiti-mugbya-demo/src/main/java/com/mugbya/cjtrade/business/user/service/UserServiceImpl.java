package com.mugbya.cjtrade.business.user.service;


import com.mugbya.cjtrade.business.user.model.User;
import com.mugbya.core.collection.Dto;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mugbya
 * @version 2014-08-21.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    List<User> userList = new ArrayList<>();

    public UserServiceImpl() {
        User user = new User("00","oo","oo");
        userList.add(user);
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public List<User> getUser(Dto dto) {
        for (User user : userList){
            System.out.println(user);
        }
        return userList;
    }

    @Test
    @Override
    public User checkUser(String username, String userpassword) {
        User user = userList.get(0);

        if (username.equals(user.getUsername()) && userpassword.equals(user.getUserpassword())  ){
            return user;
        }
        return null;
    }


}
