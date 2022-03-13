package com.changgou.seckill.service.Impl;

import com.changgou.seckill.service.SeckillOrderService;
import com.changgou.seckill.task.MultiThreadingCreateOrder;
import entity.SeckillStatus;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-07-16:52
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    //下单
    @Override
    public boolean add(String username, String time, Long id) {
        System.out.println("进入add方法");

        //Redis记录用户排队次数 避免单人多次抢单问题  increment自增
        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(username, 1);
        if(userQueueCount>1){
            throw new RuntimeException(String.valueOf(StatusCode.REPERROR));//重复操作异常
        }

        //定义用户排队对象
        SeckillStatus seckillStatus = new SeckillStatus(username,new Date(),1,id,time);

        //以队列形式存到Redis 左存右取 用户抢单排队
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(seckillStatus);

        //再存一份到Redis 用于查询抢单状态信息
        redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);

        //异步执行
        multiThreadingCreateOrder.createOrder();
        return true;


    }

    @Override
    public SeckillStatus queryStatus(String username) {
        return (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
    }
}
