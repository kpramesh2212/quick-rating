package com.ramesh.repository;

import com.ramesh.domain.Criterion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CriterionRepository extends CrudRepository<Criterion, Long>{
    @Query(value = "select * from criterion where project_id=?1", nativeQuery = true)
    Iterable<Criterion> findAll(Long projectId);
    @Query(value = "select * from criterion where project_id =?1 and criterion_id=?2", nativeQuery =
            true)
    Criterion findOne(Long projectId, Long criterionId);
}
