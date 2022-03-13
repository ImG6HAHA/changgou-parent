package com.changgou.search.service.Impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.service.SkuEsService;
import com.search.pojo.SkuInfo;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-23:47
 */

@Service
public class SkuEsServiceImpl implements SkuEsService {


    @Autowired
    private SkuEsMapper skuEsMapper;



    @Autowired
    private SkuFeign skuFeign;


    @Override
    public void importSku() {
        //feign调用 查询List<Sku>
        Result<List<Sku>> list = skuFeign.findByStatus("1");

        //将List<Sku>转成List<SkuInfo>
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(list.getData()),SkuInfo.class);

        //调用Dao实现数据批量导入
        skuEsMapper.saveAll(skuInfoList);
    }
}
