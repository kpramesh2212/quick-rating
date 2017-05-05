package com.ramesh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private Long id;
    private String name;
    private Double total = 0D;
    private List<Result> resultsPerProject;
    private List<Result> resultsPerUser;
    private List<Result> resultsPerCriterion;
    private List<Result> resultsPerProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Result> getResultsPerProject() {
        return resultsPerProject;
    }

    public void setResultsPerProject(List<Result> resultsPerProject) {
        this.resultsPerProject = resultsPerProject;
    }

    public List<Result> getResultsPerUser() {
        return resultsPerUser;
    }

    public void setResultsPerUser(List<Result> resultsPerUser) {
        this.resultsPerUser = resultsPerUser;
    }

    public List<Result> getResultsPerCriterion() {
        return resultsPerCriterion;
    }

    public void setResultsPerCriterion(List<Result> resultsPerCriterion) {
        this.resultsPerCriterion = resultsPerCriterion;
    }

    public List<Result> getResultsPerProduct() {
        return resultsPerProduct;
    }

    public void setResultsPerProduct(List<Result> resultsPerProduct) {
        this.resultsPerProduct = resultsPerProduct;
    }
}
