package com.changgou.goods.dao;

import com.changgou.goods.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-28-11:33
 */
public interface SkuMapper extends Mapper<Sku> {

    @Update("update tb_sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int decrCount(@Param("id") Long id,@Param("num") Integer num);
}
