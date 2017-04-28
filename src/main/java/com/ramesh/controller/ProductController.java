package com.ramesh.controller;

import com.ramesh.domain.Project;
import com.ramesh.repository.ProductRepository;
import com.ramesh.repository.RepositoryUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Inject
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(Principal principal) {
        List<Long> projectIds = RepositoryUtil.getProjectIdsWhereUserIsAdminOrRater(principal);
        return new ResponseEntity<>(productRepository.findAllByProjectIdsIn(projectIds), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = "projectId")
    public ResponseEntity<?> getProducts(@RequestParam("projectId") Long projectId, Principal principal) {
        Project project = RepositoryUtil.verifyAndGetProject(projectId, principal);
        return new ResponseEntity<>(project.getProducts(), HttpStatus.OK);
    }
}
