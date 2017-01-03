package com.ramesh.controller;

import com.ramesh.domain.Product;
import com.ramesh.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Inject
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Long productId) {
        return productRepository.findOne(productId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long productId) {
        productRepository.delete(productId);
    }

}
