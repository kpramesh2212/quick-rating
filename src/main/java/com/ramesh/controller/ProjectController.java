package com.ramesh.controller;

import com.ramesh.domain.Project;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.ProjectRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
    @Inject
    private ProjectRepository projectRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable Long projectId) {
        Project p = verifyAndGetProject(projectId);
        return new ResponseEntity<>(p, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        verifyAndGetProject(projectId);
        projectRepository.delete(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody Project
            project) {
        verifyAndGetProject(projectId);
        project.setId(projectId);
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Project verifyAndGetProject(Long projectId) {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }
        return project;
    }

}
