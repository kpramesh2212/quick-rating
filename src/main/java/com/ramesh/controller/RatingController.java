package com.ramesh.controller;

import com.ramesh.domain.Project;
import com.ramesh.domain.Rating;
import com.ramesh.exception.InvalidDataException;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.exception.UnAuthorizedException;
import com.ramesh.repository.RatingRepository;
import com.ramesh.repository.RepositoryUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {
    @Inject
    private RatingRepository ratingRepository;

    @GetMapping
    public ResponseEntity<?> getRatings(Principal principal,
                                        @RequestParam(required = false, defaultValue = "false") boolean admin) {
        Iterable<Rating> ratings = null;
        final String email = principal.getName();

        if (admin) {
            ratings = ratingRepository.findAllByProject_Admin_Email(email);
        } else {
            ratings = ratingRepository.findAllByRater(email);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRating(Principal principal, @Valid @RequestBody Rating rating) {
        final Long projectId = rating.getProject().getId();
        final Long productId = rating.getProduct().getId();
        final Long criterionId = rating.getCriterion().getId();
        final String raterEmail = principal.getName();
        final Project project = RepositoryUtil.getProjectWhereRater(projectId, raterEmail);
        final boolean productInProject = RepositoryUtil.isProjectContainsProduct(projectId, productId);
        final boolean criterionInProject = RepositoryUtil.isProjectContainsCriterion(projectId, productId);

        if (project == null) {
            throw new UnAuthorizedException("User cannot rate this project " + projectId);
        }
        if (!productInProject) {
            throw new ResourceNotFoundException("Product " + productId + " not in project " + projectId);
        }
        if (!criterionInProject) {
            throw new ResourceNotFoundException("Criterion " + criterionId + " not in project " + projectId);
        }

        //Lets set the rater to current user
        rating.setRater(raterEmail);
        ratingRepository.save(rating);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{ratingId}")
    public ResponseEntity<?> deleteRating(Principal principal, @PathVariable Long ratingId) {
        final String raterEmail = principal.getName();
        final Rating rating = ratingRepository.findByIdAndRater(ratingId, raterEmail);

        if (rating == null) {
            throw new ResourceNotFoundException("Rating with Id " + ratingId + " not found");
        }
        ratingRepository.delete(rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{ratingId}")
    public ResponseEntity<?> updateRating(Principal principal,
                                          @PathVariable Long ratingId,
                                          @RequestBody Rating rating) {
        final String raterEmail = principal.getName();
        Rating ratingToUpdate = ratingRepository.findByIdAndRater(ratingId, raterEmail);
        if (ratingToUpdate == null) {
            throw new ResourceNotFoundException("Rating with Id " + ratingId + " not found");
        }
        //we are only interested in updating the value
        if (rating == null || rating.getValue() == null || rating.getValue() < 1) {
            throw new InvalidDataException("Rating value should be provided for update");
        }
        ratingToUpdate.setValue(rating.getValue());
        ratingRepository.save(ratingToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
