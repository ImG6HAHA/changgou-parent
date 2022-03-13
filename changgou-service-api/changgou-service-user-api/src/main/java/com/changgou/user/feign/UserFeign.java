package com.changgou.user.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-04-22:50
 */

@FeignClient(name="user")
@RequestMapping("/user")
public interface UserFeign {


    //用户下单后添加积分
    @PostMapping("/points/add")
    Result addPoints(@RequestParam Integer points);
}
