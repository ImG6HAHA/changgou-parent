package com.changgou.seckill.controller;

import com.changgou.seckill.service.SeckillOrderService;
import entity.Result;
import entity.SeckillStatus;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-07-16:52
 */
@RestController
@RequestMapping("order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    //用户添加秒杀订单
    @RequestMapping("/add")
    public Result add(String time,Long id){
        String username="szitheima";
        seckillOrderService.add(username,time,id);
        System.out.println("1");
        System.out.println("2");
        return new Result(true, StatusCode.OK,"正在排队.....");


    }

    //抢单状态信息查询
    @GetMapping("/query")
    public Result<SeckillStatus> queryStatus(String username){
        username="szitheima";
        SeckillStatus status = seckillOrderService.queryStatus(username);

        if(status!=null){
            return new Result<SeckillStatus>(true,StatusCode.OK,"查询抢单状态信息成功",status);
        }

        return new Result(false,StatusCode.NOTFOUNDERROR,"抢单失败");
    }


}
