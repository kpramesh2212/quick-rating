package com.ramesh.controller;

import com.ramesh.domain.Product;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects/{projectId}/products")
public class ProductController {
    @Inject
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable Long projectId) {
        return new ResponseEntity<>(productRepository.findAll(projectId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable Long projectId, @PathVariable Long
            productId) {
        Product product = productRepository.findOne(projectId, productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found for" +
                    " project with id " + projectId);
        }
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

}
