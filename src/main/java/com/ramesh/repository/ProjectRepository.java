package com.ramesh.repository;

import com.ramesh.domain.Project;
import com.ramesh.domain.Rater;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>{

    Project findByIdAndAdmin_Email(Long id, String adminEmail);

    Project findById(Long id);

    Iterable<Project> findAllByAdmin_Email(String email);

    Iterable<Project> findDistinctByRatersIn(Iterable<Rater> raters);

}
