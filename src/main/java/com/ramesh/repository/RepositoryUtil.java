package com.ramesh.repository;

import com.ramesh.domain.Account;
import com.ramesh.domain.Project;
import com.ramesh.domain.Rater;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.exception.UnAuthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepositoryUtil {
    private static ProjectRepository projectRepository;
    private static RaterRepository raterRepository;
    private static AccountRepository accountRepository;

    public RepositoryUtil(@Autowired ProjectRepository projectRepository,
                          @Autowired RaterRepository raterRepository,
                          @Autowired AccountRepository accountRepository) {
        RepositoryUtil.projectRepository = projectRepository;
        RepositoryUtil.raterRepository = raterRepository;
        RepositoryUtil.accountRepository = accountRepository;
    }

    public static List<Project> getProjectsWhereUserIsAdminOrRater(Principal principal) {
        final List<Rater> raters = raterRepository.findAllByEmail(principal.getName());
        final String adminEmail = principal.getName();
        final List<Project> projects =
                projectRepository.findDistinctByAdmin_EmailOrRatersIn(adminEmail, raters);
        return projects;
    }

    public static List<Long> getProjectIdsWhereUserIsAdminOrRater(Principal principal) {
        List<Project> projects = getProjectsWhereUserIsAdminOrRater(principal);
        final List<Long> projectIds = new ArrayList<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        return projectIds;
    }

    public static void verifyProjectExists(Long projectId) {
        if (!projectRepository.exists(projectId)) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }
    }

    public static Project verifyAndGetProject(Long projectId, Principal principal) {
        RepositoryUtil.verifyProjectExists(projectId);
        final Rater rater = raterRepository.findByProjectIdAndEmail(projectId, principal.getName());
        if (rater != null) {
            return projectRepository.findById(projectId);
        }
        final Project project = projectRepository.findByIdAndAdmin_Email(projectId, principal.getName());
        if (project == null) {
            throw new UnAuthorizedException("User is not authorized to view this project " +
                    projectId);
        }
        return project;
    }

    public static Account getAdminAccount(Principal principal) {
        return accountRepository.findByEmail(principal.getName());
    }

}
