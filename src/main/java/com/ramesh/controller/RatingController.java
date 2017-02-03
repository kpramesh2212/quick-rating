package com.ramesh.controller;

import com.ramesh.domain.Rating;
import com.ramesh.repository.CriterionRepository;
import com.ramesh.repository.ProductRepository;
import com.ramesh.repository.ProjectRepository;
import com.ramesh.repository.RatingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects/{projectId}")
public class RatingController {
    @Inject
    private RatingRepository ratingRepository;
    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private ProductRepository productRepository;
    @Inject
    private CriterionRepository criterionRepository;

    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public Iterable<Rating> getAllRatingsByProjectId(@PathVariable Long projectId) {
        return ratingRepository.findAllByProjectId(projectId);
    }

    @RequestMapping(value = "/products/{productId}/ratings", method = RequestMethod.GET)
    public Iterable<Rating> getAllRatingsByProjectAndProductId(@PathVariable Long projectId,
                                                               @PathVariable Long productId) {
        return ratingRepository.findAllByProjectAndProductId(projectId, productId);
    }

    @RequestMapping(value = "/criteria/{criterionId}/ratings", method = RequestMethod.GET)
    public Iterable<Rating> getAllRatingsByProjectAndCriterionId(@PathVariable Long projectId,
                                                                 @PathVariable Long criterionId) {
        return ratingRepository.findAllByProjectAndCriterionId(projectId, criterionId);
    }

    @RequestMapping(value = "/products/{productId}/criteria/{criterionId}/ratings", method = RequestMethod.GET)
    public Iterable<Rating> getAllRatingsByProjectProductAndCriterionId(@PathVariable Long projectId,
                                                                        @PathVariable Long productId,
                                                                        @PathVariable Long criterionId) {
        return ratingRepository.findAllByProjectProductAndCriterionId(projectId, productId, criterionId);

    }

    @RequestMapping(value = "/ratings", method = RequestMethod.POST)
    public ResponseEntity<?> createRating(@RequestBody Rating rating) {
        ratingRepository.save(rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
