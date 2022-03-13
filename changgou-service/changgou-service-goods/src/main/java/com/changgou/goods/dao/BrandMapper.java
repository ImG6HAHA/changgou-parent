package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou.dao *
 * @since 1.0
 */

/**
 * Mapper<Brand>  指定通用的mapper的父接口:封装了所有的CRUD的操作
 * T  指定操作的表对应的POJO
 */
public interface BrandMapper extends Mapper<Brand> {


    //查询商品分类id对应品牌
    @Select("SELECT a.* FROM tb_brand a,tb_category_brand b WHERE  b.category_id=#{categoyrId} AND b.brand_id = a.id")
    List<Brand> findByCategoryId(Integer categoryId);
}
