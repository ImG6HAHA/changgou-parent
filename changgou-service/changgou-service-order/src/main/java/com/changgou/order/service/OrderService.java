package com.changgou.order.service;

import com.changgou.order.pojo.Order;

import java.text.ParseException;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-04-12:27
 */
public interface OrderService {

    //下订单
    void add (Order order);

    /*
    修改订单状态
        1 支付时间
        2 支付状态
    参数 订单id 支付时间 交易流水号

     */
    void updateStatus(String outtradeno,String paytime,String transactionid) throws ParseException, Exception;


    //删除订单--逻辑删除
    void deleteOrder(String outtradeno);



}
