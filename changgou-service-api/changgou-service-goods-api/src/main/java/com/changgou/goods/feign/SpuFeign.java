package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-17:30
 */

@FeignClient(name="goods")
@RequestMapping("/goods")
public interface SpuFeign {

    @GetMapping("/spu/{id}")
    Result<Spu> findById(@PathVariable Long id);

}
