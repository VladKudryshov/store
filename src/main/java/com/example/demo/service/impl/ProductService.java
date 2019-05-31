package com.example.demo.service.impl;

import com.example.demo.dao.IProductDAO;
import com.example.demo.exceptions.product.ProductAlreadyExistException;
import com.example.demo.exceptions.product.ProductNotFoundException;
import com.example.demo.models.products.Product;
import com.example.demo.service.IProductService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductDAO productDAO;

    @Override
    public Product getProduct(Integer id) {
        return productDAO.findOne(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productDAO.findByName(name).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void getProductsByNames(Set<String> names, Consumer<List<Product>> consumer) {
        productDAO.findByNames(names).ifPresent(consumer);
    }

    @Override
    public List<Product> getProductsByIds(Set<Integer> ids) {
        return productDAO.findByIds(ids).orElse(Lists.newArrayList());
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    @Override
    public void create(Product product) {
        Optional<Product> temp = productDAO.findByName(product.getName());
        if (temp.isPresent()) {
            throw new ProductAlreadyExistException();
        }
        product.validate();
        productDAO.save(product);
    }

    @Override
    public void update(Product product) {
        productDAO.findById(product.getId()).orElseThrow(ProductNotFoundException::new);
        product.validate();
        productDAO.save(product);
    }

    @Override
    public void delete(Integer id) {
        productDAO.findById(id).orElseThrow(ProductNotFoundException::new);
        productDAO.delete(id);
    }

    @Override
    public void deleteByIds(Set<Integer> ids) {
        productDAO.deleteAllById(ids);
    }

    @Override
    public Page<Product> getProductByCategory(String category, Pageable pageable) {
        return productDAO.findByCategory(category, pageable);
    }
}
