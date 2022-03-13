package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SpecMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:53
 */

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Spec spec) {
        specMapper.insertSelective(spec);
    }

    @Override
    public Spec update(Spec spec) {
        specMapper.updateByPrimaryKeySelective(spec);
        return spec;
    }

    @Override
    public void delete(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }


    //根据分类获取对应的规格
    @Override
    public List<Spec> findByCategoryId(Integer categoryId) {
        //先知道分类对应的template_id
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        //根据template_id查询对应的spec
        Spec spec = new Spec();//封装一个java.bean 如果该指定属性不为空，则将该属性作为查询条件
        spec.setTemplateId(category.getTemplateId());
        return specMapper.select(spec);

    }
}
