package com.changgou.user.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import entity.BCrypt;
import entity.JwtUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-01-20:21
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    //查询用户
    @GetMapping("/{username}")
    public Result<User> findByName( @PathVariable String username){
        User userInfo = userService.findByName(username);
        return new Result(true, StatusCode.OK,"查询用户成功",userInfo);

    }

    //获取所有用户
    @PreAuthorize("hasAnyAuthority('admin')")//权限控制注解，只有admin才能查询所有用户
    @GetMapping("/list")
    public Result<List<User>> findAll(){
        List<User> list  = userService.findAll();
        return new Result(true, StatusCode.OK,"查询所有用户成功",list);

    }

    //用户登录
    @GetMapping("/login")
    public Result login(String username, String password, HttpServletResponse response){//HttpServletResponse response 创建Cookie
        //查询用户的信息
        User user = userService.findByName(username);
        //判断密码
        if(user !=null && BCrypt.checkpw(password,user.getPassword())){
            //创建用户令牌信息
            HashMap<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("role","USER");
            tokenMap.put("success","SUCCESS");
            tokenMap.put("username",username);
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(tokenMap), null);
            //把令牌信息存到Cookie
            Cookie cookie = new Cookie("Authorization",token);
            cookie.setDomain("localhost");//所属域名
            cookie.setPath("/");//根路径
            response.addCookie(cookie);//添加cookie
            //把令牌作为参数给用户 token
            return new Result(true, StatusCode.OK,"用户登录成功",token);
        }
            return new Result(false, StatusCode.LOGINERROR,"账号或密码错误");

    }

    //用户下单后添加积分
    @PostMapping("/points/add")
    public Result addPoints(@PathVariable Integer points){
        String username ="szitheima";
        userService.addPoints(username,points);
        return new Result(true, StatusCode.OK,"积分添加成功");
    }




}
