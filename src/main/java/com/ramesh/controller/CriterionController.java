package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.CriterionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects/{projectId}/criteria")
public class CriterionController {
    @Inject
    private CriterionRepository criterionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getCriteria(@PathVariable Long projectId) {
        return new ResponseEntity<>(criterionRepository.findAll(projectId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{criterionId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCriterion(@PathVariable Long projectId, @PathVariable Long
            criterionId) {
        Criterion criterion = criterionRepository.findOne(projectId, criterionId);
        if (criterion == null) {
            throw new ResourceNotFoundException("Criterion with id " + criterionId + " not found" +
                    " for the project with id " + projectId);
        }
        return new ResponseEntity<>(criterion, HttpStatus.FOUND);
    }

}
