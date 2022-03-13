package com.changgou.pay.service;

import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-05-15:02
 */
public interface PayService {

    //统一下单(获取到二维码)
    Map createNative(Map<String,String> parameterMap);

    //查询订单状态
    Map queryStatus(String outtradeno);

}
