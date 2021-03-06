package com.changgou.seckill.timer;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import entity.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /****
     * 每5秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void loadGoodsPushRedis(){


        //获取时间段集合
        List<Date> dateMenus = DateUtil.getDateMenus();//2019-09-12 13:59:32

        //循环时间段
        for (Date startTime : dateMenus) {

            //命名空间
            String extName = DateUtil.data2str(startTime,DateUtil.PATTERN_YYYYMMDDHH);

            //根据时间段数据查询对应的秒杀商品数据
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            // 1)商品必须审核通过  status=1
            criteria.andEqualTo("status","1");
            // 2)库存>0
            criteria.andGreaterThan("stockCount",0);
            // 3)开始时间<=活动开始时间
            criteria.andGreaterThanOrEqualTo("startTime",startTime);
            // 4)活动结束时间<开始时间+2小时
            criteria.andLessThan("endTime", DateUtil.addDateHour(startTime,2));
            // 5)排除之前已经加载到Redis缓存中的商品数据
            Set keys = redisTemplate.boundHashOps("SeckillGoods_" + extName).keys();
            if(keys!=null && keys.size()>0){
                criteria.andNotIn("id",keys);
            }

            //查询数据
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);

            //将秒杀商品数据存入到Redis缓存
            for (SeckillGoods seckillGood : seckillGoods) {
                redisTemplate.boundHashOps("SeckillGoods_"+extName).put(seckillGood.getId(),seckillGood);
                //给每个商品做个队列 防止超卖
                //redisTemplate.boundListOps("SeckillGoodsQueue_"+seckillGood.getId()).leftPushAll(putAllIds(seckillGood.getStockCount(),seckillGood.getId()));
                System.out.println(startTime+"ok");

            }
        }
    }

//                public Long[] putAllIds(Integer num,Long id){
//                    Long[] ids = new Long[num];
//                    for (int i = 0; i <ids.length ; i++) {
//                        ids[i]=id;
//
//                             }
//                    return ids;
//                    }

                }
