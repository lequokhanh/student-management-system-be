package com.example.smsbe.repository;

import com.example.smsbe.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    @Query("""
    SELECT g
    FROM Grade g
    WHERE g.grade = :grade
    AND g.deletedAt IS NULL
    """)
    Optional<Grade> findByGrade(Integer grade);
}
