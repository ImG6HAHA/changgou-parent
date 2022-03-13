package com.changgou.goods.service;

import com.changgou.goods.pojo.Para;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:53
 */
public interface ParaService {

    Para findById(Integer id);


    void add(Para para);

    Para update(Para para);

    void delete(Integer id);

    //根据分类获取对应的参数
    List<Para> findByCategoryId(Integer categoyrId);


}
