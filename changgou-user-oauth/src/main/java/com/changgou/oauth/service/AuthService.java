package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-02-20:27
 */
public interface AuthService {

    /***
     * 授权认证方法
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
