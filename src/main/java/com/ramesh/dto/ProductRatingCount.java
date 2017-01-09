package com.ramesh.dto;

import java.util.List;

public class ProductRatingCount {
    private Long productId;
    private Integer total;
    private List<CriterionRatingCount> criterionRatingCountList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<CriterionRatingCount> getCriterionRatingCountList() {
        return criterionRatingCountList;
    }

    public void setCriterionRatingCountList(List<CriterionRatingCount> criterionRatingCountList) {
        this.criterionRatingCountList = criterionRatingCountList;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
