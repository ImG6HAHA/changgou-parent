package com.changgou.goods.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-26-21:53
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return new Result(true, StatusCode.OK,"新增分类成功");


    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Category> pageInfo1 = categoryService.findPage(page,size);
        return new Result<PageInfo>(true, StatusCode.OK,"分页查询成功",pageInfo1);


    }

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Category category,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Category> pageInfo2 = categoryService.findPage(category,page,size);
        return new Result<PageInfo>(true, StatusCode.OK,"按条件分页查询成功",pageInfo2);


    }

    @GetMapping("/search")
    public Result<List<Category>> findList(@RequestBody Category category){
        List<Category> list =categoryService.findList(category);
        return new Result<List<Category>>(true, StatusCode.OK,"按条件查询成功",list);


    }

    @GetMapping(value ="/list/{parentId}")
    public Result<Category> findByPrantId(@PathVariable Integer parentId ){
        //根据父节点ID查询
        List<Category> list = categoryService.findByParentId(parentId);
        return new Result<Category>(true,StatusCode.OK,"根据父节点ID查询成功",list);
    }




}
