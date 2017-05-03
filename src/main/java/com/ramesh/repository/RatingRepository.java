package com.ramesh.repository;

import com.ramesh.domain.Rating;

import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    Iterable<Rating> findAllByRater(String email);

    Iterable<Rating> findAllByProject_Admin_Email(String email);

    Rating findByIdAndRater(Long id, String rater);

}
