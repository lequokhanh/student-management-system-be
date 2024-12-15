package com.example.smsbe.repository;

import com.example.smsbe.entity.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreTypeRepository extends JpaRepository<ScoreType, Integer> {
    @Query("""
    SELECT s
    FROM ScoreType s
    WHERE s.deletedAt IS NULL
    """)
    List<ScoreType> findAllActive();

    @Query("""
    SELECT s
    FROM ScoreType s
    WHERE s.id = :id
    AND s.deletedAt IS NULL
    """)
    Optional<ScoreType> findByIdAndDeletedAtIsNull(@Param("id") Integer id);
}
