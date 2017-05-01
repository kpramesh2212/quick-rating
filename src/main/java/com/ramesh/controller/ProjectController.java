package com.ramesh.controller;

import com.ramesh.domain.Project;
import com.ramesh.exception.UnAuthorizedException;
import com.ramesh.repository.ProjectRepository;
import com.ramesh.repository.RaterRepository;
import com.ramesh.repository.RepositoryUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProjects(Principal principal,
                                         @RequestParam(required = false, defaultValue = "false") boolean admin) {
        Iterable<Project> projects = null;
        final String email = principal.getName();
        if (admin) {
            projects = projectRepository.findAllByAdmin_Email(email);
        } else {
            projects = projectRepository.findDistinctByRatersIn(raterRepository.findAllByEmail(email));
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable Long projectId, Principal principal) {
        Project p = RepositoryUtil.verifyAndGetProject(projectId, principal);
        return new ResponseEntity<>(p, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, Principal
            principal) {
        project.setAdmin(RepositoryUtil.getAdminAccount(principal));
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
        project.setAdmin(RepositoryUtil.getAdminAccount(principal));
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyProjectForEditing(Long projectId, String adminEmail) {
        RepositoryUtil.verifyProjectExists(projectId);
        final Project project = projectRepository.findByIdAndAdmin_Email(projectId, adminEmail);
        if (project == null) {
            throw new UnAuthorizedException("User unauthorized to edit/delete this project " +
                    projectId);
        }
    }

}
