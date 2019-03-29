package com.example.demo.controllers;

import com.example.demo.models.products.Product;
import com.example.demo.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "2") Integer size,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) String category) {
        if(StringUtils.isNotBlank(category)){
            return productService.getProductByCategory(category, new PageRequest(page, size));
        }
        return productService.getProducts(new PageRequest(page, size));
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public List<Product> getProductByIds(@RequestBody Set<Integer> ids) {
        return productService.getProductsByIds(ids);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void saveProduct(@RequestBody Product product) {
        productService.create(product);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody Product product) {
        productService.update(product);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void deleteProduct(@RequestBody Set<Integer> ids) {
        productService.deleteByIds(ids);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

}
