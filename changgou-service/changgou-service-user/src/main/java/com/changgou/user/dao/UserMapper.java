package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-01-20:20
 */
public interface UserMapper extends Mapper<User> {


    //下单后添加积分
    @Update("update tb_user set points=points+#{points} where username=#{username}")
    void addPoints(@Param("username") String username, @Param("pionts") Integer points);
}
