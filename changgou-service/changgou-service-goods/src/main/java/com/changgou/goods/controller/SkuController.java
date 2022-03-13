package com.changgou.goods.controller;

import com.changgou.goods.pojo.Sku;
import com.changgou.goods.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-21:22
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;


    @GetMapping("/status/{status}")
    public Result<List<Sku>> findByStatus(@PathVariable(name = "status") String status){
        List<Sku> skuList = skuService.findByStatus(status);
        return new Result<List<Sku>>(true, StatusCode.OK,"根据状态查询成功",skuList);

    }

    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable Long id){
        Sku sku = skuService.findById(id);
        return new Result<Sku>(true, StatusCode.OK,"根据id查询成功",sku);

    }

    /*
    商品信息递减
    Map<key,value>  key:要递减的商品id value:要递减的数量
    Map参数要用@RequestParam注解
     */

    @PostMapping("/decr/count")
    public Result decrCount(@RequestParam Map<String,Integer> decrMap){

        skuService.decrCount(decrMap);
        return new Result<Sku>(true, StatusCode.OK,"库存递减成功");
    }



}
