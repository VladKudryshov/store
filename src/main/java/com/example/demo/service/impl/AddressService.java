package com.example.demo.service.impl;

import com.example.demo.dao.IAddressDAO;
import com.example.demo.exceptions.address.AddressAlreadyExistException;
import com.example.demo.exceptions.address.AddressNotFoundException;
import com.example.demo.exceptions.user.UserNotAuthenticated;
import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IAddressService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    IAddressDAO addressDAO;

    @Autowired
    IUserService userService;

    @Override
    public Address addAddressToUser(Address address) {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();
        List<Address> addresses = addressDAO.findAllByUserId(authenticatedUser.getId());
        if (addresses.stream().anyMatch(f -> f.equals(address))) {
            throw new AddressAlreadyExistException();
        }
        address.setUserId(authenticatedUser.getId());
        address.validate();
        return addressDAO.save(address);
    }

    public Address chooseAddress(Address address) {
        try {
            userService.getAuthenticatedUser();
            return addressDAO.findOne(address.getId());
        } catch (UserNotAuthenticated e) {
            address.validate();
            return addressDAO.save(address);
        }
    }

    @Override
    public Address changeAddress(Address address) {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();
        addressDAO.findAddressByIdAndUserId(address.getId(), authenticatedUser.getId())
                .orElseThrow(AddressNotFoundException::new);
        address.validate();
        return addressDAO.save(address);
    }

    @Override
    public List<Address> getAddresses() {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();
        return addressDAO.findAllByUserId(authenticatedUser.getId());
    }

    @Override
    public void deleteAddress(Integer id) {
        Address address = Optional.ofNullable(addressDAO.findOne(id)).orElseThrow(AddressNotFoundException::new);
        addressDAO.delete(address);
    }

}
