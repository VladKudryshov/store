package com.example.demo.service;

import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;

import java.util.List;

public interface IAddressService {

    Address addAddressToUser(Address address);

    Address chooseAddress(Address address);

    Address changeAddress(Address address);

    void deleteAddress(Integer id);

    List<Address> getAddresses();

}
