package com.ramesh.dto;

import java.util.List;

public class ProjectRatingCount {
    private Long projectId;
    private Integer total;
    private List<ProductRatingCount> productRatingCountList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ProductRatingCount> getProductRatingCountList() {
        return productRatingCountList;
    }

    public void setProductRatingCountList(List<ProductRatingCount> productRatingCountList) {
        this.productRatingCountList = productRatingCountList;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
