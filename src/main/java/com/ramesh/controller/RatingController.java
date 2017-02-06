package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.domain.Product;
import com.ramesh.domain.Rating;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.CriterionRepository;
import com.ramesh.repository.ProductRepository;
import com.ramesh.repository.RatingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/projects/{projectId}")
public class RatingController {
    @Inject
    private RatingRepository ratingRepository;
    @Inject
    private ProductRepository productRepository;
    @Inject
    private CriterionRepository criterionRepository;

    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Rating>>
    getAllRatingsByProjectId(@PathVariable Long projectId) {
        return new ResponseEntity<>(ratingRepository.findAllByProjectId(projectId), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}/ratings", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Rating>>
    getAllRatingsByProjectAndProductId(@PathVariable Long projectId, @PathVariable Long productId) {
        return new ResponseEntity<>(ratingRepository.findAllByProjectAndProductId(projectId,
                productId), HttpStatus.OK);
    }

    @RequestMapping(value = "/criteria/{criterionId}/ratings", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Rating>>
    getAllRatingsByProjectAndCriterionId(@PathVariable Long projectId,
                                         @PathVariable Long criterionId) {
        return new ResponseEntity<>(ratingRepository.findAllByProjectAndCriterionId(projectId,
                criterionId), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}/criteria/{criterionId}/ratings", method =
            RequestMethod.GET)
    public ResponseEntity<Iterable<Rating>>
    getAllRatingsByProjectProductAndCriterionId(@PathVariable Long projectId,
                                                @PathVariable Long productId,
                                                @PathVariable Long criterionId) {
        return new ResponseEntity<>(ratingRepository.findAllByProjectProductAndCriterionId
                (projectId, productId, criterionId), HttpStatus.OK);

    }

    @RequestMapping(value = "/ratings", method = RequestMethod.POST)
    public ResponseEntity<?> createRating(@Valid @RequestBody Rating rating) {
        Long projectId = rating.getProject().getId();
        Long productId = rating.getProduct().getId();
        Long criterionId = rating.getCriterion().getId();

        Product product = productRepository.findOne(projectId, productId);
        Criterion criterion = criterionRepository.findOne(projectId, criterionId);
        if (product == null || criterion == null) {
            throw new ResourceNotFoundException("Supplied Product with Id " + productId + " and " +
                    "Criterion with id " + criterionId + " could not be found in the project with" +
                    " id " + projectId);
        }
        ratingRepository.save(rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
