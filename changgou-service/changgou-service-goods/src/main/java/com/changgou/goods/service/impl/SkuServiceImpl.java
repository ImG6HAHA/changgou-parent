package com.changgou.goods.service.impl;

import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-21:18
 */
@Service
public class SkuServiceImpl implements SkuService {



    @Autowired
    private SkuMapper skuMapper;


    @Override
    public List<Sku> findByStatus(String status) {
        Sku sku = new Sku();
        sku.setStatus(status);
        return skuMapper.select(sku);
    }

    @Override
    public Sku findById(Long id) {
        return skuMapper.selectByPrimaryKey(id);
    }


    //商品下单后库存递减
    @Override
    public void decrCount(Map<String, Integer> decrMap) {
        for (Map.Entry<String, Integer> entry : decrMap.entrySet()) {
            //商品id
            Long id = Long.valueOf(entry.getKey());
            //递减数量
            Object obj = entry.getValue();
            Integer num = Integer.valueOf(obj.toString());

            //库存数量>=递减数量 采用行级锁控制超卖 update tb_sku set num=num-#{num} where id=#{id} and num>=#{num}
            int count=skuMapper.decrCount(id,num);//count 受影响的行数
            if(count<=0){
                throw new RuntimeException("库存不足");
            }
        }


    }
}
