package com.ramesh.repository;

import com.ramesh.domain.Criterion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CriterionRepository extends CrudRepository<Criterion, Long>{

    @Query(value = "select * from criterion where project_id =?1 and criterion_id=?2", nativeQuery = true)
    Criterion findOne(Long projectId, Long criterionId);

    @Query(value = "select * from criterion where project_id in ?1", nativeQuery = true)
    Iterable<Criterion> findAllByProjectIdsIn(List<Long> projectId);

}
