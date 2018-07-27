package com.zjy.redis.service;

import com.zjy.redis.pojo.MisiUsers;

public interface UserService {

    public MisiUsers addUser(MisiUsers user);

    public MisiUsers getUserByUserId(String userId);

    public void updateUserByUser(MisiUsers user);
}
