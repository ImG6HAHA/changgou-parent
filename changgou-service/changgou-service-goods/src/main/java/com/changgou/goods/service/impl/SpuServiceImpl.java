package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import entity.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR Z
 * @CREATE 2022-01-28-11:35
 */

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private IdWorker idWorker;//使用idWorker
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;



    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public Goods saveGoods(Goods goods) {
        //spu一个
        Spu spu = goods.getSpu();

        //商品修改操作：根据spu_id判断 如果为空，则增加 如果不为空，则修改(修改spu,删除之前的List<Sku>,新增List<Sku>)
        //判断spu_id是否为空
        if(spu.getId()==null){
            //为空 新增
            spu.setId(idWorker.nextId());//生成id
            spuMapper.insertSelective(spu);
        }else{
            //不为空 修改
            spuMapper.updateByPrimaryKeySelective(spu);
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        //sku集合
        Date date = new Date();
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());//用三级分类id去查category
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());//用品牌id查brand
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {//集合用for循环遍历增加
            sku.setId(idWorker.nextId());
            String name = spu.getName();//spu的name
            //防止空指针(值传空)
            if(StringUtils.isEmpty(sku.getSpec())){
                sku.setSpec("{}");//给个空的对象防止其值为空
            }

            Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);//{"手机屏幕尺寸":"5.5寸","网络":"联通2G","颜色":"黑","测试":"实施","机身内存":"16G","存储":"16G","像素":"300万像素"} JSON格式，需要转成Map格式，再遍历出来
            for (Map.Entry<String, String> Entry : specMap.entrySet()) {//遍历Map
                name+="  "+ Entry.getValue();
            }
            sku.setName(name);//sku的name=spu的name + 规格信息
            sku.setCreateTime(date);//定义好创建时间
            sku.setUpdateTime(date);//定义好更新时间
            sku.setSpuId(spu.getId());//定义spu_id
            sku.setCategoryId(spu.getCategory3Id());//三级分类(在spu里)
            sku.setCategoryName(category.getName());//分类名字
            sku.setBrandName(brand.getName());//品牌名字

            skuMapper.insertSelective(sku);

        }

        return goods;
    }

    @Override
    public Goods findGoodsById(long spuId) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //查询sku集合
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> list = skuMapper.select(sku);//也可以在skuMapper里面直接写SQL语句
        //封装Goods
        Goods goods = new Goods();
        goods.setSkuList(list);
        goods.setSpu(spu);
        return goods;//返回goods

    }

    @Override
    public void audit(long spuId) {
        //查询商品
        Spu spu=spuMapper.selectByPrimaryKey(spuId);
        //判断是否符合审核条件
        if(spu.getIsDelete().equalsIgnoreCase("1")){//equalsIgnoreCase 忽略大小写比较
            throw new RuntimeException("不能对已经删除的商品审核");//throw new RuntimeException 抛出异常信息
        }
        //审核改变状态为已审核
        spu.setStatus("1");
        //上架
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);//更新此商品
    }

    @Override
    public void pull(long spuId) {
        Spu spu=spuMapper.selectByPrimaryKey(spuId);
        if(spu.getIsDelete().equalsIgnoreCase("1")){//equalsIgnoreCase 忽略大小写比较
            throw new RuntimeException("不能下架已经删除的商品");//throw new RuntimeException 抛出异常信息
        }
        spu.setIsMarketable("0");//下架
        spuMapper.updateByPrimaryKeySelective(spu);//更新此商品
    }

    @Override
    public void up(long spuId) {
        Spu spu=spuMapper.selectByPrimaryKey(spuId);
        if(spu.getIsDelete().equalsIgnoreCase("1")){//equalsIgnoreCase 忽略大小写比较
            throw new RuntimeException("不能上架已经删除的商品");//throw new RuntimeException 抛出异常信息
        }
        if(!spu.getStatus().equalsIgnoreCase("1")){
            throw new RuntimeException("不能上架未通过审核的商品");
        }
        spu.setIsMarketable("1");//上架
        spuMapper.updateByPrimaryKeySelective(spu);//更新此商品

    }

    @Override//批量上架商品
    public void upMany(Long[] spuIds) {
        //构建SQL  update tb_sku set is_marktable=1 where id in (spuIds) and is_delete=0 and status=1;
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();//自定义查询
        //id in (spuIds)
        criteria.andIn("id",Arrays.asList(spuIds));//将spuIds数组转化为List集合  Arrays.asList():集合转数组
        //is_delete=0
        criteria.andEqualTo("isDelete","0");
        //status=1
        criteria.andEqualTo("status","1");

        //准备修改的数据
        Spu spu = new Spu();
        spu.setIsMarketable("1");//上架
        spuMapper.updateByExampleSelective(spu,example);


    }

    //逻辑删除
    @Override
    public void logicDelete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //先判断是否下架
        if(spu.getIsMarketable().equalsIgnoreCase("1")){
            throw new RuntimeException("已上架的商品不能删除");
        }
        spu.setIsDelete("1");//逻辑删除
        spu.setStatus("0");//初始化审核状态
        spuMapper.updateByPrimaryKey(spu);//更新一下

    }

    @Override
    public void restore(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断是否被删除
        if(spu.getIsDelete().equalsIgnoreCase("0")){
            throw new RuntimeException("该商品未被删除");
        }
        spu.setIsDelete("0");
        spuMapper.updateByPrimaryKey(spu);
    }

    @Override
    public void delete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if(spu.getIsDelete().equalsIgnoreCase("0")){
            throw new RuntimeException("此商品不能删除");
        }
        spuMapper.deleteByPrimaryKey(spu);
    }
}
