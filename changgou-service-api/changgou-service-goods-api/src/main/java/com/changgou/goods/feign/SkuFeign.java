package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-21:57
 *
 */
@FeignClient(name="goods")
@RequestMapping("/sku")
public interface SkuFeign {


    @GetMapping("/status/{status}")//查询已上架的sku----用于导入到ES
    Result<List<Sku>> findByStatus(@PathVariable(name = "status") String status);


    @GetMapping("/{id}")
    Result<Sku> findById(@PathVariable Long id);


    //库存递减
    @PostMapping("/decr/count")
    Result decrCount(@RequestParam Map<String,Integer> decrMap);

}
