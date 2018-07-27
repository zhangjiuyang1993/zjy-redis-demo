package com.zjy.redis.mapper;

import java.util.List;

import com.zjy.redis.pojo.MisiUsers;
import com.zjy.redis.pojo.MisiUsersExample;
import org.apache.ibatis.annotations.Param;

public interface MisiUsersMapper {
    int countByExample(MisiUsersExample example);

    int deleteByExample(MisiUsersExample example);

    int deleteByPrimaryKey(String userId);

    int insert(MisiUsers record);

    int insertSelective(MisiUsers record);

    List<MisiUsers> selectByExample(MisiUsersExample example);

    MisiUsers selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") MisiUsers record, @Param("example") MisiUsersExample example);

    int updateByExample(@Param("record") MisiUsers record, @Param("example") MisiUsersExample example);

    int updateByPrimaryKeySelective(MisiUsers record);

    int updateByPrimaryKey(MisiUsers record);
}