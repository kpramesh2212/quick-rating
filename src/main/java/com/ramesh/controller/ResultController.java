package com.ramesh.controller;

import com.ramesh.domain.Rating;
import com.ramesh.dto.Result;
import com.ramesh.repository.RatingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/results")
public class ResultController {
    @Inject private RatingRepository ratingRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResult(Principal principal,
                                    @RequestParam(required = false, defaultValue = "false") boolean admin) {
        final String email = principal.getName();
        final List<Result> results = new ArrayList<>();
        Iterable<Rating> ratings = null;

        if (admin) {
            ratings = ratingRepository.findAllByProject_Admin_Email(email);
        } else {
            ratings = ratingRepository.findAllByRater(email);
        }
        for (Rating rating : ratings) {
            //Per Project
            double total = rating.getValue() * rating.getCriterion().getWeight();
            Long projectId = rating.getProject().getId();
            Result projectResult = find(results, projectId, (result, id) -> result.getId() == id);
            if (projectResult == null) {
                projectResult = new Result();
                results.add(projectResult);
            }
            projectResult.setId(projectId);
            projectResult.setName(rating.getProject().getName());
            projectResult.setTotal(projectResult.getTotal() + total);
        }
       return new ResponseEntity<>(results, HttpStatus.OK);
    }


    private <T, U> T find(Iterable<T> iterable, U t, BiPredicate<T, U> biPredicate) {
        for (T it : iterable) {
            if (biPredicate.test(it, t)) {
                return it;
            }
        }
        return null;
    }

}
