package com.ramesh.repository;

import com.ramesh.domain.Account;
import com.ramesh.domain.Criterion;
import com.ramesh.domain.Product;
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
    private static ProductRepository productRepository;
    private static CriterionRepository criterionRepository;
    private static AccountRepository accountRepository;

    public RepositoryUtil(@Autowired ProjectRepository projectRepository,
                          @Autowired RaterRepository raterRepository,
                          @Autowired AccountRepository accountRepository,
                          @Autowired ProductRepository productRepository,
                          @Autowired CriterionRepository criterionRepository) {
        RepositoryUtil.projectRepository = projectRepository;
        RepositoryUtil.raterRepository = raterRepository;
        RepositoryUtil.accountRepository = accountRepository;
        RepositoryUtil.productRepository = productRepository;
        RepositoryUtil.criterionRepository = criterionRepository;
    }

    public static Iterable<Long> getProjectIdsByEmail(String email, boolean admin) {
        if (admin) {
            return getProjectIds(projectRepository.findAllByAdmin_Email(email));
        }
        Iterable<Rater> raters = raterRepository.findAllByEmail(email);
        return getProjectIds(projectRepository.findDistinctByRatersIn(raters));
    }


    private static Iterable<Long> getProjectIds(Iterable<Project> projects) {
        List<Long> projectIds = new ArrayList<>();

        for (Project project: projects) {
            projectIds.add(project.getId());
        }

        return projectIds;
    }

    public static void verifyProjectExists(Long projectId) {
        if (!projectRepository.exists(projectId)) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }
    }

    public static Project getProjectWhereRater(Long projectId, String raterEmail) {
        final Rater rater = raterRepository.findByProjectIdAndEmail(projectId, raterEmail);
        if (rater != null) {
            return projectRepository.findById(projectId);
        }
        return null;
    }

    public static Project verifyAndGetProject(Long projectId, Principal principal) {
        RepositoryUtil.verifyProjectExists(projectId);
        Project project = getProjectWhereRater(projectId, principal.getName());
        if (project != null) {
            return project;
        }
        project = projectRepository.findByIdAndAdmin_Email(projectId, principal.getName());
        if (project == null) {
            throw new UnAuthorizedException("User is not authorized to view this project " +
                    projectId);
        }
        return project;
    }

    public static boolean isProjectContainsProduct(Long projectId, Long productId) {
         Product product = productRepository.findOne(projectId, productId);
         if (product == null) {
             return false;
         }
         return true;
    }

    public static boolean isProjectContainsCriterion(Long projectId, Long criterionId) {
        Criterion criterion = criterionRepository.findOne(projectId, criterionId);
        if (criterion == null) {
            return false;
        }
        return true;
    }

    public static Account getAdminAccount(Principal principal) {
        return accountRepository.findByEmail(principal.getName());
    }

}
