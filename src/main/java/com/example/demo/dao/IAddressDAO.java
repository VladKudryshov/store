package com.example.demo.dao;

import com.example.demo.models.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAddressDAO extends JpaRepository<Address, Integer> {
    List<Address> findAllByUserId(String userUID);
    Optional<Address> findAddressByIdAndUserId(Integer id, String userId);
}
