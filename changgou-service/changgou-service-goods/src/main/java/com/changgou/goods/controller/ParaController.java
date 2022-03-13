package com.changgou.goods.controller;

import com.changgou.goods.pojo.Para;

import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.ParaService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:52
 */


@RestController
@RequestMapping("/para")
public class ParaController {

    @Autowired
    private ParaService paraService;

    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable Integer id){
        Para para = paraService.findById(id);
        return  new Result<Para>(true, StatusCode.OK,"查询参数成功",para);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Para para){
        paraService.add(para);
        return  new Result(true, StatusCode.OK,"添加参数成功");
    }

    @PutMapping("/{id}")
    public Result<Para> update(@RequestBody Para para, @PathVariable Integer id){
        para.setId(id);
        Para paraUdt = paraService.update(para);
        return  new Result<Para>(true, StatusCode.OK,"更新参数成功",paraUdt);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        paraService.delete(id);
        return  new Result(true, StatusCode.OK,"删除参数成功");
    }

    @GetMapping("/category/{categoyrId}")
    public Result<List<Para>> findByCategoryId(@PathVariable Integer categoyrId){
        List<Para> list = paraService.findByCategoryId(categoyrId);
        return new Result<List<Para>>(true, StatusCode.OK,"根据分类获取对应的参数成功",list);
    }





}
