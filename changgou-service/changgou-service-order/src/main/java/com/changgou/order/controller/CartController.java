package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-15:38
 */

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public Result add(Integer num, Long id){
        String username ="szitheima";
        cartService.add(num,id,username);
        return new Result(true, StatusCode.OK,"加入购物车成功");
    }

    @GetMapping("/orderItems")
    public Result<List<OrderItem>> findOrderItem(){
        String username ="szitheima";
        List<OrderItem> orderItems = cartService.findOrderItem(username);
        return new Result<List<OrderItem>>(true, StatusCode.OK,"查询购物车成功",orderItems);
    }


}
