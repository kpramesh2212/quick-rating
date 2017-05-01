package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.domain.Project;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.CriterionRepository;

import com.ramesh.repository.RepositoryUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/criteria")
public class CriterionController {
    @Inject
    private CriterionRepository criterionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getCriteria(Principal principal,
                                         @RequestParam(required = false, defaultValue = "false") boolean admin) {
        Iterable<Long> productIds = RepositoryUtil.getProjectIdsByEmail(principal.getName(), admin);
        return new ResponseEntity<>(criterionRepository.findAllByProjectIdsIn(productIds), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"projectId"})
    public ResponseEntity<?> getCriterion(@RequestParam Long projectId, Principal principal) {
        Project project = RepositoryUtil.verifyAndGetProject(projectId, principal);
        return new ResponseEntity<>(project.getCriteria(), HttpStatus.FOUND);
    }

}
