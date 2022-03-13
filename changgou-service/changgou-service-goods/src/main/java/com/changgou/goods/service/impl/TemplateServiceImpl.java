package com.changgou.goods.service.impl;

import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-14:46
 */
@Service
public class TemplateServiceImpl implements TemplateService {


    @Autowired
    private TemplateMapper templateMapper;


    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Template template) {
        templateMapper.insertSelective(template);
    }

    @Override
    public Template update(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
        return template;
    }

    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);

    }
}
