package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou *
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
//注意 要使用通用的mapper的组件扫描
@MapperScan(basePackages = {"com.changgou.goods.dao"})
// mapper接口继承了通用的mapper
//默认提供一些方法:
//   insert
//   update
//   delete
//   select
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    //IdWorker产生唯一不重复id的工具类
    @Bean           //将IdWorker交给Spring容器
    public IdWorker idWorker(){
        return new IdWorker(0,1);
    }
}
