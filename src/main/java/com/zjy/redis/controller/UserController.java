package com.zjy.redis.controller;

import com.zjy.redis.pojo.MisiUsers;
import com.zjy.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author zhangjiuyang
 * @create 2018/7/27
 * @since 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/addUser")
    @ResponseBody
    public Object addUser() {
        MisiUsers user = new MisiUsers();
        user.setUsername("zhangsan");
        user.setUserId(UUID.randomUUID().toString());
        MisiUsers resultUser = userService.addUser(user);
        return resultUser;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Object getUser(String userId) {
        MisiUsers user = userService.getUserByUserId(userId);
        return user;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public void updateUser() {
        MisiUsers user = new MisiUsers();
        user.setUserId("80ff9123-a4a1-4cc9-9f88-cf8053ce0b51");
        user.setUsername("lisi");
        userService.updateUserByUser(user);
    }
}
