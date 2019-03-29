package com.example.demo.dao;

import com.example.demo.models.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProductDAO extends JpaRepository<Product, Integer> {
    Optional<Product> findById(Integer id);

    Optional<Product> findByName(String name);

    void deleteAllById(Set<Integer> ids);

    @Query(value = "SELECT * FROM product WHERE id IN (:ids)", nativeQuery = true)
    Optional<List<Product>> findByIds(@Param("ids") Set<Integer> ids);

    @Query(value = "SELECT * FROM product WHERE name IN (:names)", nativeQuery = true)
    Optional<List<Product>> findByNames(@Param("names") Set<String> names);

    Page<Product> findByCategory(String category, Pageable pageable);


}
