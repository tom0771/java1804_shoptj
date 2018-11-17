package com.qf.dao;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressDao {

    List<Address> queryAddressByUid(Integer uid);

    int adddizhi(Address address);

    Address queryById(Integer id);
}
