package com.changgou.pay.service.Impl;

import com.changgou.pay.service.PayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-05-15:02
 */
@Service
public class PayServiceImpl implements PayService {

    //注入微信支付信息配置
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.notifyurl}")
    private String notifyurl;

    @Override
    public Map createNative(Map<String, String> parameterMap) {

        try {
            //1、参数封装
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appid", appid);                               //应用ID
            paramMap.put("mch_id", partner);                            //商户ID号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());    //随机数
            paramMap.put("body", "商品");                            	//订单描述
            paramMap.put("out_trade_no",parameterMap.get("outtradeno"));  //商户订单号
            paramMap.put("total_fee", parameterMap.get("totalfee"));       //交易金额
            paramMap.put("spbill_create_ip", "127.0.0.1");              //终端IP
            paramMap.put("notify_url", notifyurl);                      //回调地址
            paramMap.put("trade_type", "NATIVE");                       //交易类型

            //2、将参数转成xml字符，并携带签名
            String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //3、执行请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();

            //4、获取返回的数据
            String result = httpClient.getContent();
            //返回的数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map queryStatus(String outtradeno) {
        try {
            //1、参数封装
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appid", appid);                               //应用ID
            paramMap.put("mch_id", partner);                            //商户ID号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());    //随机数
            paramMap.put("out_trade_no",outtradeno);  //商户订单号

            //2、将参数转成xml字符，并携带签名
            String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //3、执行请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();

            //4、获取返回的数据
            String result = httpClient.getContent();
            //返回的数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
