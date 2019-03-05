package com.example.demo.dao;

import com.example.demo.models.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProductDAO extends MongoRepository<Product, String> {
    Optional<Product> findById(String id);

    Optional<Product> findByName(String name);

    @Query("{_id: { $in: ?0 } })")
    Optional<List<Product>> findByIds(Set<String> ids);
}
