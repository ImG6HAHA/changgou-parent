package com.changgou.content.feign;

import com.changgou.content.pojo.Content;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-17:02
 */

@FeignClient(name = "content")
@RequestMapping("/content")
public interface ContentFeign {


    //根据分类id查询广告-----用于存到Redis
    @GetMapping("/list/{categoryId}")
    Result<List<Content>> findByCategoryId(@PathVariable(name = "categoryId") Long  categoryId);

}
