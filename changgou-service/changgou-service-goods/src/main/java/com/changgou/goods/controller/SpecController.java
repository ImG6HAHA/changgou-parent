package com.changgou.goods.controller;

import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import entity.Result;
import entity.StatusCode;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-17:52
 */

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable Integer id){
       Spec spec = specService.findById(id);
        return  new Result<Spec>(true, StatusCode.OK,"查询规格成功",spec);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Spec spec){
        specService.add(spec);
        return  new Result(true, StatusCode.OK,"添加规格成功");
    }

    @PutMapping("/{id}")
    public Result<Spec> update(@RequestBody Spec spec,@PathVariable Integer id){
        spec.setId(id);
        Spec specUdt = specService.update(spec);
        return  new Result<Spec>(true, StatusCode.OK,"更新规格成功",specUdt);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        specService.delete(id);
        return  new Result(true, StatusCode.OK,"删除规格成功");
    }

    @GetMapping("/category/{categoyrId}")
    public Result<List<Spec>> findByCategoryId(@PathVariable Integer categoyrId){
        List<Spec> list = specService.findByCategoryId(categoyrId);
        return new Result<List<Spec>>(true, StatusCode.OK,"根据分类获取对应的规格成功",list);
    }

}
