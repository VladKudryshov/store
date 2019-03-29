package com.example.demo.service;

import com.example.demo.models.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface IProductService {

    Product getProduct(Integer id);

    Product getProductByName(String name);

    void getProductsByNames(Set<String> names, Consumer<List<Product>> consumer);

    Page<Product> getProducts(Pageable pageable);

    void create(Product product);

    void update(Product product);

    void delete(Integer id);

    void deleteByIds(Set<Integer> ids);

    Page<Product> getProductByCategory(String category, Pageable pageable);

    List<Product> getProductsByIds(Set<Integer> ids);
}
