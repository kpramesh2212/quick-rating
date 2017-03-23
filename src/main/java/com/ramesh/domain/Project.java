package com.ramesh.domain;


import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "PROJECT_ID")
    private Long id;

    @Column(name = "PROJECT_NAME")
    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    @Size(min = 1)
    @NotNull
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    @Size(min = 1)
    @NotNull
    private Set<Criterion> criteria;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(Set<Criterion> criteria) {
        this.criteria = criteria;
    }
}
