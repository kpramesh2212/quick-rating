package com.ramesh.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "PROJECT_ID")
    private Long id;
    @Column(name = "PROJECT_NAME")
    private String name;
    @ManyToMany
    @JoinColumn(name = "PROJECT_ID")
    private Set<Product> products;
    @ManyToMany
    @JoinColumn(name = "PROJECT_ID")
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
