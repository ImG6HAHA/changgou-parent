package com.changgou.goods.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;
import entity.Page;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-26-21:51
 */
public interface CategoryService {

    void add (Category category);//增加商品分类

    PageInfo<Category> findPage(int page, int size);//分页查询

    PageInfo<Category> findPage(Category category,int page,int size);//按条件分页查询 和上面的方法构成重载

    List<Category> findList(Category category);//按条件查询

    List<Category> findByParentId( Integer ParentId);//根据父节点id查询







}
