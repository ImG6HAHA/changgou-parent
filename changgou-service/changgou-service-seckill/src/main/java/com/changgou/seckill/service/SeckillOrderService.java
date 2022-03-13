package com.changgou.seckill.service;

import entity.SeckillStatus;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-07-16:52
 */
public interface SeckillOrderService {
    //下单
    boolean add(String username, String time, Long id);

    //查询秒杀订单状态
    SeckillStatus queryStatus(String username);

}
