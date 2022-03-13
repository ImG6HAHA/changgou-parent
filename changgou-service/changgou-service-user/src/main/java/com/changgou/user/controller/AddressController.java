package com.changgou.user.controller;

import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-23:02
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    @GetMapping("/list")
    public Result<List<Address>> findAddress(String username){
        List<Address> list = addressService.findAddress(username);
        return new Result<List<Address>>(true, StatusCode.OK,"查询地址成功",list);

    }

}
