package com.ramesh.repository;

import com.ramesh.domain.Rater;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RaterRepository extends CrudRepository<Rater, Long> {
    List<Rater> findAllByEmail(String email);
    @Query(value = "select * from rater where project_id=? and email =?", nativeQuery = true)
    Rater findByProjectIdAndEmail(Long projectId, String email);
}
