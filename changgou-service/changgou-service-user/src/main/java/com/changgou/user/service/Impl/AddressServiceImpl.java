package com.changgou.user.service.Impl;

import com.changgou.user.dao.AddressMapper;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-03-23:03
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> findAddress(String username) {
        Address address = new Address();
        address.setUsername(username);
        return addressMapper.select(address);

    }
}
