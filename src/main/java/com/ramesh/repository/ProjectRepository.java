package com.ramesh.repository;

import com.ramesh.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long>{
}
