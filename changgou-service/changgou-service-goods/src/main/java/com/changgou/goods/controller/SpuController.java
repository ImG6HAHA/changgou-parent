package com.changgou.goods.controller;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.SpuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-28-11:35
 */

@RestController
@RequestMapping("/goods")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/spu/{id}")
    public Result<Spu> findById(@PathVariable Long id){
        Spu spu =spuService.findById(id);
        return new Result<Spu>(true, StatusCode.OK,"根据id查询spu成功",spu);

    }




    @PostMapping("/save")
    public Result<Goods> saveGoods(@RequestBody Goods goods){
        Goods good =spuService.saveGoods(goods);
        return new Result<Goods>(true, StatusCode.OK,"添加商品成功",good);

    }

    @GetMapping("/{spuId}")
    public Result<Goods> findGoodsById(@PathVariable long spuId ){
        Goods goodsById = spuService.findGoodsById(spuId);
        return  new Result<Goods>(true, StatusCode.OK,"根据spuId查询商品成功",goodsById);
    }

    @PutMapping("/audit/{spuId}")
    public Result audit(@PathVariable long spuId){
        spuService.audit(spuId);
        return  new Result(true, StatusCode.OK,"审核商品操作成功");
    }

    @PostMapping("/pull/{spuId}")
    public Result pull(@PathVariable long spuId){
        spuService.pull(spuId);
        return  new Result(true, StatusCode.OK,"商品下架成功");
    }

    @PostMapping("/up/{spuId}")
    public Result up(@PathVariable long spuId){
        spuService.up(spuId);
        return  new Result(true, StatusCode.OK,"商品上架成功");
    }

    @PutMapping("/up/many")
    public Result upMany(@RequestBody Long[] spuIds){
        spuService.upMany(spuIds);
        return new Result(true,StatusCode.OK,"批量上架商品成功");
    }

    @DeleteMapping("/logicDelete/{spuId}")
    public Result logicDelete(@PathVariable Long spuId){
        spuService.logicDelete(spuId);
        return new Result(true,StatusCode.OK,"逻辑删除商品成功");
    }

    @PutMapping("/restore/{spuId}")
    public Result restore(@PathVariable Long spuId){
        spuService.restore(spuId);
        return new Result(true,StatusCode.OK,"恢复商品成功");
    }

    @DeleteMapping("/delete/{spuId}")
    public Result delete(@PathVariable Long spuId){
        spuService.delete(spuId);
        return new Result(true,StatusCode.OK,"删除商品成功");
    }


}
