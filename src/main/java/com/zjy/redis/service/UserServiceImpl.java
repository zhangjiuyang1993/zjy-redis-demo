package com.zjy.redis.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjy.redis.jedis.JedisClient;
import com.zjy.redis.mapper.MisiUsersMapper;
import com.zjy.redis.pojo.MisiUsers;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangjiuyang
 * @create 2018/7/26
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private MisiUsersMapper usersMapper;

    //用户唯一标识
    @Value("${USER_IDENTITY}")
    private String USER_IDENTITY;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public MisiUsers addUser(MisiUsers user) {
        int count = usersMapper.insert(user);
        if (count > 0) {
            logger.info("数据添加到数据库--->" + count);
            Long result = jedisClient.hset(USER_IDENTITY, user.getUserId(), JSONObject.toJSONString(user));
            logger.info("数据添加到Redis--->" + result);
            return user;
        }
        return null;
    }

    @Override
    public MisiUsers getUserByUserId(String userId) {
        MisiUsers user = null;
        String result = jedisClient.hget(USER_IDENTITY, userId);
        if (!StringUtils.isEmpty(result)) {
            logger.info("数据从Redis中获取。。。。。");
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            return JSONObject.toJavaObject(jsonObject, MisiUsers.class);
        } else {
            logger.info("数据从数据库中获取。。。。。");
            user = usersMapper.selectByPrimaryKey(userId);
        }
        return user;
    }

    @Override
    public void updateUserByUser(MisiUsers user) {
        String userId = user.getUserId();
        String result = jedisClient.hget(USER_IDENTITY, userId);
        if (!StringUtils.isEmpty(result)) {
            logger.info("从缓存中获取数据。。。。。");
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            MisiUsers users = JSONObject.toJavaObject(jsonObject, MisiUsers.class);
            String upUserId = users.getUserId();
            users.setUsername(user.getUsername());
            Long lCount = jedisClient.hset(USER_IDENTITY, upUserId, JSONObject.toJSONString(users));
            logger.info("Redis修改数据。。。。lcount" + lCount);
            MisiUsers record = new MisiUsers();
            record.setUserId(userId);
            record.setUsername(user.getUsername());
            usersMapper.updateByPrimaryKeySelective(record);
        } else {
            logger.info("从数据库中获取数据。。。。。");
            MisiUsers record = new MisiUsers();
            record.setUserId(userId);
            record.setUsername(user.getUsername());
            usersMapper.updateByPrimaryKeySelective(record);
            Long lCount = jedisClient.hset(USER_IDENTITY, userId, JSONObject.toJSONString(record));
        }
    }
}
