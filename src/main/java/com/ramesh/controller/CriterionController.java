package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.domain.Product;
import com.ramesh.repository.CriterionRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/criteria")
public class CriterionController {
    @Inject
    private CriterionRepository criterionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Criterion> getCriteria() {
        return criterionRepository.findAll();
    }

    @RequestMapping(value = "/{criterionId}", method = RequestMethod.GET)
    public Criterion getCriterion(@PathVariable Long criterionId) {
        return criterionRepository.findOne(criterionId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createCriterion(@RequestBody Criterion criterion) {
        criterionRepository.save(criterion);
    }

    @RequestMapping(value = "/{criterionId}", method = RequestMethod.DELETE)
    public void deleteCriterion(@PathVariable Long criterionId) {
        criterionRepository.delete(criterionId);
    }

}
