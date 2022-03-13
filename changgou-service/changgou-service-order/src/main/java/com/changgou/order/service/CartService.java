package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-15:39
 */
public interface CartService {

    //加入购物车--加入数量、商品id
    void add(Integer num,Long id,String username);

    //购物车集合查询
    //因为存的时候是根据用户名往Redis中存储用户的购物车数据的，所以我们这里可以将用户的名字作为key去Redis中查询对应的数据。
    List<OrderItem> findOrderItem(String username);
}
