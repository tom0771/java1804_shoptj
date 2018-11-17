package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IAddressDao;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressDao addressDao;

    @Override
    public List<Address> queryAddressByUid(Integer uid) {

        return addressDao.queryAddressByUid(uid);
    }

    @Override
    public Address adddizhi(Address address) {
        int aid = addressDao.adddizhi(address);
        return address;
    }
}
