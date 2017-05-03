package com.ramesh.controller;

import com.ramesh.domain.Criterion;
import com.ramesh.domain.Product;
import com.ramesh.domain.Project;
import com.ramesh.domain.Rating;
import com.ramesh.dto.CriterionRatingCount;
import com.ramesh.dto.ProductRatingCount;
import com.ramesh.dto.ProjectRatingCount;
import com.ramesh.repository.ProjectRepository;
import com.ramesh.repository.RatingRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/projects/{projectId}")
public class ResultController {
    @Inject private RatingRepository ratingRepository;
    @Inject private ProjectRepository projectRepository;

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ProjectRatingCount getResult(@PathVariable Long projectId) {
        ProjectRatingCount prjCount = new ProjectRatingCount();
        Integer projectTotal = 0;
        Project project = projectRepository.findOne(projectId);
        Set<Product> products = project.getProducts();
        Set<Criterion> criteria = project.getCriteria();
        List<ProductRatingCount> productRatingCountList = new ArrayList<>();

        for (Product product : products) {
            ProductRatingCount prCount = new ProductRatingCount();
            Integer productTotal = 0;
            List<CriterionRatingCount> criterionRatingCountList= new ArrayList<>();

            for (Criterion criterion : criteria) {
                CriterionRatingCount crCount = new CriterionRatingCount();
                crCount.setCriterionId(criterion.getId());
                Integer criterionTotal = getTotalRatingForCriterion(projectId, product.getId(), criterion.getId());
                crCount.setTotal(criterionTotal);
                criterionRatingCountList.add(crCount);
                productTotal += criterionTotal;
            }
            projectTotal += productTotal;

            prCount.setProductId(product.getId());
            prCount.setTotal(productTotal);
            prCount.setCriterionRatingCountList(criterionRatingCountList);
            productRatingCountList.add(prCount);
        }

        prjCount.setProjectId(projectId);
        prjCount.setTotal(projectTotal);
        prjCount.setProductRatingCountList(productRatingCountList);
        return prjCount;

    }


    private Integer getTotalRatingForCriterion(Long projectId, Long productId, Long criterionId) {
        Iterable<Rating> ratings = null;
        int total = 0;
        for (Rating rating : ratings) {
            total += rating.getValue() * rating.getCriterion().getWeight();
        }
        return total;
    }

}
