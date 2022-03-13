package com.changgou.order.service.Impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.order.dao.OrderItemMapper;
import com.changgou.order.dao.OrderMapper;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.OrderService;
import com.changgou.user.feign.UserFeign;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-04-12:27
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private UserFeign userFeign;


    @Override
    public void add(Order order) {


        order.setId(String.valueOf(idWorker.nextId()));//主键id

        int totalNum=0;
        int totalMoney=0;
        List<OrderItem> orderItems =new ArrayList<OrderItem>();

        //获取勾选商品skuId,将要下单的商品添加到明细集合里,再从购物车移除
        for (Long skuId : order.getSkuIds()) {
            orderItems.add((OrderItem) redisTemplate.boundHashOps("Cart_"+order.getUsername()).get(skuId));
            redisTemplate.boundHashOps("Cart_"+order.getUsername()).delete(skuId);
        }
        //封装Map<String,Integer> 封装递减数据
        Map<String,Integer> decrMap = new HashMap<String,Integer>();

        //获取订单明细 遍历出数量和金额
        for (OrderItem orderItem : orderItems) {
            totalNum+=orderItem.getNum();
            totalMoney+=orderItem.getMoney();
            //订单明细id
            orderItem.setId(String.valueOf(idWorker.nextId()));
            //订单明细所属的订单
            orderItem.setOrderId(order.getId());
            orderItem.setIsReturn("0");//未退货

            //封装递减数据
            decrMap.put(orderItem.getSkuId().toString(),orderItem.getNum());
        }
        //订单添加一次 Order
        order.setTotalNum(totalNum);//数量合计-----购物车数量合计
        order.setTotalMoney(totalMoney);//金额合计----购物车金额合计
        order.setPayMoney(totalMoney);//实付金额
        order.setSourceType("1");//订单来源 web
        order.setPayStatus("0");//支付状态 未支付
        order.setOrderStatus("0");//订单状态 未完成
        order.setIsDelete("0");//未删除
        order.setCreateTime(new Date());//创建时间
        order.setUpdateTime(order.getCreateTime());//更新时间

        orderMapper.insertSelective(order);//添加订单

        //明细添加多次 OrderItem
        for (OrderItem orderItem : orderItems) {
            orderItemMapper.insertSelective(orderItem);
        }

        //库存递减
        skuFeign.decrCount(decrMap);
        //添加积分  因为user服务令牌问题 这地方有bug
        //userFeign.addPoints(10);
    }

    @Override
    public void updateStatus(String outtradeno, String paytime, String transactionid) throws Exception {
        //时间转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date paytimeInfo = simpleDateFormat.parse(paytime);

        //查询订单
        Order order = orderMapper.selectByPrimaryKey(outtradeno);
        //修改订单信息
        order.setPayTime(paytimeInfo);
        order.setTransactionId(transactionid);
        order.setPayStatus("1");

        //更新此条数据
        orderMapper.updateByPrimaryKeySelective(order);


    }

    @Override
    public void deleteOrder(String outtradeno) {
        Order order = orderMapper.selectByPrimaryKey(outtradeno);
        order.setIsDelete("1");
        order.setPayStatus("2");//支付失败

        orderMapper.updateByPrimaryKeySelective(order);
    }
}
