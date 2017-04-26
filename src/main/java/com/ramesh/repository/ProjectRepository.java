package com.ramesh.repository;

import com.ramesh.domain.Project;
import com.ramesh.domain.Rater;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>{
    List<Project> findDistinctByAdmin_EmailOrRatersIn(String adminEmail, List<Rater> rater);
    Project findByIdAndAdmin_Email(Long id, String adminEmail);
    Project findById(Long id);
}
