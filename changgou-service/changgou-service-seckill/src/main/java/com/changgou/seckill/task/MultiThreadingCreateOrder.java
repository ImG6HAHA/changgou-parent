package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import entity.IdWorker;
import entity.SeckillStatus;
import entity.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-07-19:41
 */

@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private IdWorker idWorker;

    //该方法异步执行（底层多线程方式）
    @Async
    public void createOrder(){
        try {
            System.out.println("进来等一会才下单");
            Thread.sleep(10000);

            //从redis队列中取出用户排队信息
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();
            if(seckillStatus==null){
                return;
            }
            //定义参与抢购商品的用户信息
            String time=seckillStatus.getTime();
            String username =seckillStatus.getUsername();
            Long id =seckillStatus.getGoodsId();

            //查询秒杀商品
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);
            //判断有没有库存
            if(seckillGoods==null){
                throw new RuntimeException("已卖完");
            }
            //创建订单对象
            SeckillOrder seckillOrder = new SeckillOrder();
            //将订单对象属性封装并储存
            seckillOrder.setSeckillId(id);//商品id
            seckillOrder.setId(idWorker.nextId());//用idWorker生成订单id
            seckillOrder.setMoney(seckillGoods.getCostPrice());//金额
            seckillOrder.setUserId(username);
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setStatus("0");

            //存到Redis
            redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

            //库存递减
            seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
            //如果商品是最后一个 同步到MySQL 删除redis数据
            if(seckillGoods.getStockCount()<=0){
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).delete(id);
            }else{
                //如果商品不是最后一个 同步数据到redis
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).put(id,seckillGoods);
            }

            //下单完成 更新状态
            seckillStatus.setOrderId(seckillOrder.getId());//订单id
            seckillStatus.setMoney(Float.valueOf(seckillGoods.getCostPrice()));//支付金额
            seckillStatus.setStatus(2);//待支付状态
            redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);//存入

            System.out.println("下单完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
