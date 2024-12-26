package com.example.smsbe.repository;

import com.example.smsbe.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    @Query("""
    SELECT g
    FROM Grade g
    WHERE g.grade = :grade
    AND g.deletedAt IS NULL
    """)
    Optional<Grade> findByGradeAndDeletedAtIsNull(Integer grade);

    Optional<Grade> findByIdAndDeletedAtIsNull(Integer id);

    @Query("""
    SELECT g
    FROM Grade g
    WHERE g.deletedAt IS NULL
    """)
    List<Grade> findAllByDeletedAtIsNull();

    @Query("""
    SELECT g
    FROM Grade g
    WHERE g.grade = :grade
    """)
    Optional<Grade> findByGrade(Integer grade);
}
