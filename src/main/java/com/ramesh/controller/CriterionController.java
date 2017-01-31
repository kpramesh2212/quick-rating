package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.repository.CriterionRepository;

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
    public Iterable<Criterion> getCriteria(@PathVariable Long projectId) {
        return criterionRepository.findAll(projectId);
    }

    @RequestMapping(value = "/{criterionId}", method = RequestMethod.GET)
    public Criterion getCriterion(@PathVariable Long projectId, @PathVariable Long criterionId) {
        return criterionRepository.findOne(projectId, criterionId);
    }

}
