package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.ParaMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:53
 */

@Service
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Para para) {
        paraMapper.insertSelective(para);

    }

    @Override
    public Para update(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
        return para;
    }

    @Override
    public void delete(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Para> findByCategoryId(Integer categoyrId) {
        //先知道分类对应的template_id
        Category category = categoryMapper.selectByPrimaryKey(categoyrId);
        Para para = new Para();//封装一个java.bean 如果该指定属性不为空，则将该属性作为查询条件
        para.setTemplateId(category.getTemplateId());
        return paraMapper.select(para);

    }
}
