package com.changgou.content.service.Impl;

import com.changgou.content.dao.ContentMapper;
import com.changgou.content.pojo.Content;
import com.changgou.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-15:19
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;


    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        Content content = new Content();
        content.setCategoryId(categoryId);
        content.setStatus("1");
        return contentMapper.select(content);

    }

    @Override
    public void addContet(Content content) {
        contentMapper.insertSelective(content);
    }

    @Override
    public void deleteContent(Integer id) {
        contentMapper.deleteByPrimaryKey(id);


    }

    @Override
    public void updateContent(Content content) {
        contentMapper.updateByPrimaryKeySelective(content);
    }
}
