package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-26-21:52
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
            categoryMapper.insertSelective(category);
    }

    @Override
    public PageInfo<Category> findPage(int page, int size) {
        PageHelper.startPage(page,size);//静态分页

        return new PageInfo<Category>(categoryMapper.selectAll());//分页查询

    }

    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        PageHelper.startPage(page,size);//静态分页
        Example example = createExample(category);
        return new PageInfo<Category>(categoryMapper.selectByExample(example));
    }

    @Override
    public List<Category> findList(Category category) {
        Example example = createExample(category);
        return  categoryMapper.selectByExample(example);
    }


    private Example createExample(Category category) {
        Example example=new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();//固定写法
        if(category!=null){
            // 分类ID
            if(!StringUtils.isEmpty(category.getId())){
                criteria.andEqualTo("id",category.getId());
            }
            // 分类名称
            if(!StringUtils.isEmpty(category.getName())){
                criteria.andLike("name","%"+category.getName()+"%");
            }
            // 商品数量
            if(!StringUtils.isEmpty(category.getGoodsNum())){
                criteria.andEqualTo("goodsNum",category.getGoodsNum());
            }
            // 是否显示
            if(!StringUtils.isEmpty(category.getIsShow())){
                criteria.andEqualTo("isShow",category.getIsShow());
            }
            // 是否导航
            if(!StringUtils.isEmpty(category.getIsMenu())){
                criteria.andEqualTo("isMenu",category.getIsMenu());
            }
            // 排序
            if(!StringUtils.isEmpty(category.getSeq())){
                criteria.andEqualTo("seq",category.getSeq());
            }
            // 上级ID
            if(!StringUtils.isEmpty(category.getParentId())){
                criteria.andEqualTo("parentId",category.getParentId());
            }
            // 模板ID
            if(!StringUtils.isEmpty(category.getTemplateId())){
                criteria.andEqualTo("templateId",category.getTemplateId());
            }
        }
                return example;



    }

    @Override
    public List<Category> findByParentId(Integer parentId) {
        Category category = new Category();//封装一个java.bean 如果该指定属性不为空，则将该属性作为查询条件
        category.setParentId(parentId);
        return categoryMapper.select(category);

    }



}
