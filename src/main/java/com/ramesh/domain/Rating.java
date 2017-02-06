package com.ramesh.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "RATING_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    @NotNull
    private Project project;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CRITERION_ID")
    @NotNull
    private Criterion criterion;

    private Integer value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
