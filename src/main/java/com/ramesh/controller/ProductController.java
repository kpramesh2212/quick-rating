package com.ramesh.controller;

import com.ramesh.domain.Product;
import com.ramesh.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects/{projectId}/products")
public class ProductController {
    @Inject
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getProducts(@PathVariable Long projectId) {
        return productRepository.findAll(projectId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Long projectId, @PathVariable Long productId) {
        return productRepository.findOne(projectId, productId);
    }

}
