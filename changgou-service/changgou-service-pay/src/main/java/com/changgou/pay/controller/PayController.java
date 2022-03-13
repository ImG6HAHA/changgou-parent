package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.PayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-05-15:24
 */
@RestController
@RequestMapping("/wechat/pay")
public class PayController {

    @Autowired
    private PayService payService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //统一下单
    @GetMapping("/create/native")
    public Result createNative(@RequestParam Map<String, String> parameterMap) {
        Map<String, String> resultMap = payService.createNative(parameterMap);
        return new Result(true, StatusCode.OK, "创建二维码预付订单成功！", resultMap);
    }

    //查询订单状态
    @GetMapping("/status/query")
    public Result queryStatus(String outtradeno) {
        Map<String, String> resultMap = payService.queryStatus(outtradeno);
        return new Result(true, StatusCode.OK, "查询订单状态成功！", resultMap);
    }

    //支付结果通知回调方法
    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request) throws Exception {

        //读取网络输入流
        ServletInputStream inStream = request.getInputStream();
        //创建一个OutputStream输入到文件中
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        // 将支付回调数据转换成xml字符串
        String xmlresult = new String(outSteam.toByteArray(), "utf-8");
        System.out.println(xmlresult);
        //将xml字符串转换成Map结构
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlresult);
        System.out.println(resultMap);

        //发送支付结果给MQ
        rabbitTemplate.convertAndSend("exchange.order","queue.order", JSON.toJSONString(resultMap));

        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        return result;
    }


}





