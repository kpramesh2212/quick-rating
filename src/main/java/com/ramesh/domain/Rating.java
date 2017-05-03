package com.ramesh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(value = {"id", "rater"}, allowGetters = true)
@Table(
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UNIQUE_RATING",
                    columnNames = { "PROJECT_ID", "PRODUCT_ID", "CRITERION_ID", "RATER" }
            )
        }
)
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

    @NotNull
    private Integer value = 1;

    private String rater;

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

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }

}
