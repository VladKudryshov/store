package com.example.demo.dao;

import com.example.demo.models.user.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAddressDAO extends MongoRepository<Address, String> {
}
