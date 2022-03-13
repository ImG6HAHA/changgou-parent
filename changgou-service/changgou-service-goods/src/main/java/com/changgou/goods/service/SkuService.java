package com.changgou.goods.service;

import com.changgou.goods.pojo.Sku;

import java.util.List;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-21:16
 */
public interface SkuService {

    //根据状态查sku
    List<Sku> findByStatus(String status);

    //根据id查sku
    Sku findById (Long id);

    //商品下单后库存递减
    void decrCount(Map<String, Integer> decrMap);
}
