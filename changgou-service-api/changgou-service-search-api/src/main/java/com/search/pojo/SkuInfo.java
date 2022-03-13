package com.search.pojo;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
//数据导入流程如下
//        1.请求search服务,调用数据导入地址
//        2.根据注册中心中的注册的goods服务的地址，使用Feign方式查询所有已经审核的Sku
//        3.使用SpringData Es将查询到的Sku集合导入到ES中


/**
 1.可能会根据商品名称搜素，而且可以搜索商品名称中的任意一个词语，所以需要分词
 2.可能会根据商品分类搜索，商品分类不需要分词
 3.可能会根据商品品牌搜索，商品品牌不需要分词
 4.可能会根据商品商家搜索，商品商家不需要分词
 5.可能根据规格进行搜索，规格时一个键值对结构，用Map
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-20:13
 */
    @Document(indexName = "skuinfo",type = "docs")
    public class SkuInfo implements Serializable {
        //商品id，同时也是商品编号
        @Id
        private Long id;

        //SKU名称
        @Field(type = FieldType.Text, analyzer = "ik_smart")
        private String name;

        //商品价格，单位为：元
        @Field(type = FieldType.Double)
        private Long price;

        //库存数量
        private Integer num;

        //商品图片
        private String image;

        //商品状态，1-正常，2-下架，3-删除
        private String status;

        //创建时间
        private Date createTime;

        //更新时间
        private Date updateTime;

        //是否默认
        private String isDefault;

        //SPUID
        private Long spuId;

        //类目ID
        private Long categoryId;

        //类目名称
        @Field(type = FieldType.Keyword)
        private String categoryName;

        //品牌名称
        @Field(type = FieldType.Keyword)
        private String brandName;

        //规格
        private String spec;

        //规格参数
        private Map<String,Object> specMap;

        //...略
    }

