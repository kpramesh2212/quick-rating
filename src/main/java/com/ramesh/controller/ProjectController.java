package com.ramesh.controller;

import com.ramesh.domain.Account;
import com.ramesh.domain.Project;
import com.ramesh.domain.Rater;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.exception.UnAuthorizedException;
import com.ramesh.repository.AccountRepository;
import com.ramesh.repository.ProjectRepository;
import com.ramesh.repository.RaterRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private RaterRepository raterRepository;
    @Inject
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProjects(Principal principal) {
        final List<Rater> raters = raterRepository.findAllByEmail(principal.getName());
        final String adminEmail = principal.getName();
        final List<Project> projects =
                projectRepository.findDistinctByAdmin_EmailOrRatersIn(adminEmail, raters);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable Long projectId, Principal principal) {
        Project p = verifyAndGetProject(projectId, principal.getName());
        return new ResponseEntity<>(p, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, Principal principal) {
        project.setAdmin(getAdminAccount(principal));
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId, Principal principal) {
        verifyProjectForEditing(projectId, principal.getName());
        projectRepository.delete(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @Valid @RequestBody Project
            project, Principal principal) {
        verifyProjectForEditing(projectId, principal.getName());
        project.setId(projectId);
        project.setAdmin(getAdminAccount(principal));
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyProjectForEditing(Long projectId, String adminEmail) {
        verifyProjectExists(projectId);
        final Project project = projectRepository.findByIdAndAdmin_Email(projectId, adminEmail);
        if (project == null) {
            throw new UnAuthorizedException("User unauthorized to edit/delete this project " +
                    projectId);
        }
    }

    private void verifyProjectExists(Long projectId) {
        if (!projectRepository.exists(projectId)) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }
    }

    private Project verifyAndGetProject(Long projectId, String adminEmail) {
        verifyProjectExists(projectId);
        final Rater rater = raterRepository.findByProjectIdAndEmail(projectId, adminEmail);
        if (rater != null) {
            return projectRepository.findById(projectId);
        }
        final Project project = projectRepository.findByIdAndAdmin_Email(projectId, adminEmail);
        if (project == null) {
            throw new UnAuthorizedException("User is not authorized to view this project " + projectId);
        }
        return project;
    }

    private Account getAdminAccount(Principal principal) {
        return accountRepository.findByEmail(principal.getName());
    }

}
