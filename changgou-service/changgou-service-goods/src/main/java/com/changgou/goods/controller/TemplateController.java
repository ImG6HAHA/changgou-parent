package com.changgou.goods.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import entity.Result;
import entity.StatusCode;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-26-14:58
 */

@RestController
@RequestMapping("/template")
public class TemplateController {


    @Autowired
    private TemplateService templateService;


    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id){
        Template template = templateService.findById(id);
        return new Result<Template>(true, StatusCode.OK,"查询模板成功",template);

    }


    @PostMapping("/add")
    public Result add(@RequestBody Template template){
        templateService.add(template);
        return new Result(true, StatusCode.OK,"添加模板成功");

    }

    @PutMapping("/{id}")
    public Result<Template> update(@RequestBody Template template,@PathVariable Integer id){
        template.setId(id);//获得id
        Template templateUdt = templateService.update(template);
        return new Result<Template>(true, StatusCode.OK,"更新模板成功",templateUdt);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        templateService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");

    }


}
