package com.changgou.content.service;

import com.changgou.content.pojo.Content;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-15:18
 */
public interface ContentService {

    //根据分类id查询广告
    List<Content> findByCategoryId(Long categoryId);

    //添加广告
    void addContet(Content content);



    //删除广告
    void deleteContent(Integer id);



    //修改广告
    void updateContent(Content content);


}
