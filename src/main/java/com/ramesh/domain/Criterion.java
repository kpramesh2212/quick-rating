package com.ramesh.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Criterion {
    @Id
    @GeneratedValue
    @Column(name = "CRITERION_ID")
    private Long id;

    @Column(name = "CRITERION_NAME")
    @NotEmpty
    private String name;

    @Column(name = "WEIGHT")
    private Integer weight = 1;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
