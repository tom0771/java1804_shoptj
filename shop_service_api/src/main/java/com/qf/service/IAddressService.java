package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressService {

    List<Address> queryAddressByUid(Integer uid);

    Address adddizhi(Address address);
}
