package com.changgou.order.controller;

import com.changgou.order.pojo.Order;
import com.changgou.order.service.OrderService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-04-12:27
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/add")
    public Result add(@RequestBody Order order){
        order.setUsername("szitheima");//设置username
        orderService.add(order);
        return new Result(true, StatusCode.OK,"下单成功");
    }

}
