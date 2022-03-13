package com.changgou.goods.service;

import com.changgou.goods.pojo.Spec;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:52
 */
public interface SpecService {

    Spec findById(Integer id);


    void add(Spec spec);

    Spec update(Spec spec);

    void delete(Integer id);

    //根据分类获取对应的规格
    List<Spec> findByCategoryId(Integer categoryId );
}
