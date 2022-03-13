package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-28-11:34
 */
public interface SpuService {

    //根据id查询spu
    Spu findById(Long id);

    //新增商品(修改商品)
    Goods saveGoods(Goods goods);

    //根据spu的id查询商品
    Goods findGoodsById(long spuId);

    //审核商品
    void audit(long spuId);

    //下架商品
    void pull(long spuId);

    //上架商品
    void up(long spuId);

    //批量上架
    void upMany(Long[] spuIds);

    //逻辑删除
    void logicDelete(Long spuId);

    //还原被删除的商品
    void restore(Long spuId);

    //物理删除商品
    void delete(Long spuId);


}
