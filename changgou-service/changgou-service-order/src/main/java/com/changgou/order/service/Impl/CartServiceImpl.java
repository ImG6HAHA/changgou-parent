package com.changgou.order.service.Impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-15:39
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void add(Integer num, Long id,String username) {
        if(num<=0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);//如果添加数量<=0 则移除该商品
            return;//必须要有return
        }

        /**
         * 查询商品详情 用feign查询 依赖goods-api
         * 1 查询sku
         * 2 查询spu
         */
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();//获取feign查询的sku
        Result<Spu> spuResult = spuFeign.findById(sku.getSpuId());
        Spu spu = spuResult.getData();//获取feign查询的spu

        OrderItem orderItem = createOrderItem(num, sku, spu);

        //将购物车数据存到Redis:namespace--(使用redis 储存键值对时可以用NameSpace的方法将键值对分在不同的命名空间，方便管理)
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);

    }
    //方法抽取 alt+ctrl+m     创建一个OrderItem对象
    private OrderItem createOrderItem(Integer num, Sku sku, Spu spu) {
        OrderItem orderItem =new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num *orderItem.getPrice());//单价*数量
        orderItem.setImage(sku.getImage());
        return orderItem;
    }

    @Override
    public List<OrderItem> findOrderItem(String username) {
        //Redis查询购物车的数据
        List<OrderItem> orderItems = redisTemplate.boundHashOps("Cart_"+username).values();
        return orderItems;
    }

}
