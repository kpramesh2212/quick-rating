package com.ramesh.repository;

import com.ramesh.domain.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    @Query(value = "select * from Rating where PROJECT_ID=?1", nativeQuery = true)
    Iterable<Rating> findAllByProjectId(Long projectId);

    @Query(value = "select * from Rating where PROJECT_ID=?1 and PRODUCT_ID=?2", nativeQuery = true)
    Iterable<Rating> findAllByProjectAndProductId(Long projectId, Long productId);

    @Query(value = "select * from Rating where PROJECT_ID=?1 and CRITERION_ID=?2", nativeQuery = true)
    Iterable<Rating> findAllByProjectAndCriterionId(Long projectId, Long criterionId);

    @Query(value = "select * from Rating where PROJECT_ID=?1 and PRODUCT_ID=?2 and CRITERION_ID=?3", nativeQuery = true)
    Iterable<Rating> findAllByProjectProductAndCriterionId(Long projectId, Long productId, Long criterionId);
}
