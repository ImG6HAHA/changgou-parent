package com.changgou.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-02-13:01
 * 全局过滤器------实现用户权限鉴别（校验）
 *泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类
 *
 */

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {//Ordered 执行顺序

    private static final String AUTHORIZE_TOKEN = "Authorization";//令牌头名字

    //全局拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();//请求
        ServerHttpResponse response = exchange.getResponse();//返回值


        //从三个地方获取用户令牌信息
        //1 头文件 第一个
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        //boolean  true:令牌在头文件中  false:令牌不在头文件中----将令牌封装到头文件中，再传递给其他微服务
        boolean hasToken =true;
        //2 参数
        if(StringUtils.isEmpty(token)){
             token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken =false;
        }
        //3 Cookie
        if(StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(cookie !=null){
                token = cookie.getValue();
            }
        }

        //如果没有令牌 则拦截
        if(StringUtils.isEmpty(token)){
            //设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }

        //如果有令牌 校验令牌是否有效
        try {
            //JwtUtil.parseJWT(token);       //用try catch 捕获异常 不能解析就拦截，能解析就有效
            //将令牌放到头文件中
            request.mutate().header(AUTHORIZE_TOKEN,"Bearer "+token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
            return chain.filter(exchange);//有效放行
    }


    //排序
    @Override
    public int getOrder() {
        return 0;
    }
}
