package com.changgou.search.controller;

import com.changgou.search.service.SkuEsService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-01-1:24
 */

@RestController
@RequestMapping("/search")
public class SkuEsController {


    @Autowired
    private SkuEsService skuEsService;

    @GetMapping("/import")
    public Result  importSku(){
        skuEsService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！");
    }
}
