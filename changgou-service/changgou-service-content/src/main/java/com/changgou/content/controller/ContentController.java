package com.changgou.content.controller;

import com.changgou.content.pojo.Content;
import com.changgou.content.service.ContentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-15:18
 */

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    @GetMapping("/list/{categoryId}")
    public Result<List<Content>> findByCategoryId(@PathVariable(name = "categoryId") Long  categoryId){
        List<Content> list = contentService.findByCategoryId(categoryId);
        return  new Result<List<Content>>(true, StatusCode.OK,"根据分类id查询广告成功",list);
    }

    @PostMapping("/add")
    public Result addContet(@RequestBody Content content){
        contentService.addContet(content);
        return  new Result(true, StatusCode.OK,"添加广告成功");
    }


    @PutMapping("/update/{id}")
    public Result updateContent(@PathVariable Integer id,@RequestBody Content content){
        content.setId(id);//获取传的id
        contentService.updateContent(content);
        return  new Result(true, StatusCode.OK,"更新广告成功");
    }


    @DeleteMapping("/delete/{id}")
    public Result deleteContent(@PathVariable Integer id){
        contentService.deleteContent(id);
        return  new Result(true, StatusCode.OK,"删除广告成功");
    }





}
