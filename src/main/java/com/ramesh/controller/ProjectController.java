package com.ramesh.controller;

import com.ramesh.domain.Project;
import com.ramesh.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
    @Inject
    private ProjectRepository projectRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public Project getProject(@PathVariable Long projectId) {
        return projectRepository.findOne(projectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createProject(@RequestBody Project project) {
        projectRepository.save(project);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable Long projectId) {
       projectRepository.delete(projectId);
    }

}
