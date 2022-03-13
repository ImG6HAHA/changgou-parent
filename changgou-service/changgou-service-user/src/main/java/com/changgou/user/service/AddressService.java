package com.changgou.user.service;

import com.changgou.user.pojo.Address;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-23:02
 */
public interface AddressService {

    //根据username查询地址
    List<Address> findAddress(String username);
}
