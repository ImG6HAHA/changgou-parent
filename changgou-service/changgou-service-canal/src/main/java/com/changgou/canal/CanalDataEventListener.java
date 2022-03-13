package com.changgou.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.xpand.starter.canal.annotation.*;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-01-31-13:01
 */


//实现mysql监听 通过feigen查询实现数据同步

@CanalEventListener
public class CanalDataEventListener  {

    //监听类调用feign
    @Autowired
    private ContentFeign contentFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//使用redisTemplate将feign查询出的数据存储到redis中

    //自定义数据库监听类
    @ListenPoint(destination = "example",schema = "changgou_content", table = {"tb_content", "tb_content_category"},eventType = {
            CanalEntry.EventType.UPDATE,
            CanalEntry.EventType.DELETE,
            CanalEntry.EventType.INSERT
    }
    )
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1.获取列名 为category_id的值
        String categoryId = getColumnValue(eventType, rowData);
        //2.调用feign 获取该分类下的所有的广告集合
        Result<List<Content>> categoryresult = contentFeign.findByCategoryId(Long.valueOf(categoryId));
        List<Content> data = categoryresult.getData();
        //3.使用redisTemplate存储到redis中                                    将对象转化为Json字符串
        stringRedisTemplate.boundValueOps("content_" + categoryId).set(JSON.toJSONString(data));
    }

    private String getColumnValue(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String categoryId = "";
        //判断 如果是删除  则获取beforlist
        if (eventType == CanalEntry.EventType.DELETE) {
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        } else {
            //判断 如果是添加 或者是更新 获取afterlist
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }
        return categoryId;
    }

//    //增加监听-----获取增加后的数据
//    @InsertListenPoint
//    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData){//rowData行数据
//
//        //rowData.getAfterColumnsList() 获取增加后的数据
//        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
//            System.out.println(("列名：" + column.getName() + "------新增的数据：" + column.getValue()));
//
//        }
//
//    }
//
//    @DeleteListenPoint
//    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData){//rowData行数据
//
//        //rowData.getBeforeColumnsList() 获取删除前的数据
//        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
//            System.out.println(("列名：" + column.getName() + "------删除的数据：" + column.getValue()));
//
//        }
//
//    }
//    @UpdateListenPoint
//    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {//rowData行数据
//
//        //rowData.getBeforeColumnsList() 获取修改前的数据
//        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
//            System.out.println(("列名：" + column.getName() + "------变更前的数据：" + column.getValue()));
//
//        }
//        //rowData.getBeforeColumnsList() 获取修改后的数据
//        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
//            System.out.println(("列名：" + column.getName() + "------变更后的数据：" + column.getValue()));
//
//        }
//
//    }

}
