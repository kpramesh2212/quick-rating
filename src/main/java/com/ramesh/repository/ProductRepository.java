package com.ramesh.repository;


import com.ramesh.domain.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select * from product where project_id =?1 and product_id=?2", nativeQuery = true)
    Product findOne(Long projectId, Long productId);

    @Query(value = "select * from product where project_id in ?1", nativeQuery = true)
    Iterable<Product> findAllByProjectIdsIn(Iterable<Long> projectId);

}
