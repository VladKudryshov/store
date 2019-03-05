package com.example.demo.controllers;

import com.example.demo.dao.IProductDAO;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "products")
public class ProductController {

    @Autowired
    IProductDAO productDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size) {
        return productDAO.findAll(new PageRequest(page, size));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Product saveProduct(@RequestBody Product product) {
        return productDAO.save(product);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        Product oldProduct = productDAO.findByName(product.getName()).orElseThrow(UserNotFoundException::new);
        oldProduct.setName(product.getName());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setPrice(product.getPrice());
        return productDAO.save(oldProduct);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void deleteProduct(@PathVariable String id) {
        productDAO.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable String id) {
       return productDAO.findById(id).orElse(new Product());
    }


}
