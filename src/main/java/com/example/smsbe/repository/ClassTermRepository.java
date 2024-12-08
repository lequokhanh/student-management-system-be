package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClassTermRepository extends JpaRepository<ClassTerm, Integer> {
    @Query("""
    SELECT ct
    FROM ClassTerm ct
    WHERE ct.aClass.id = :classId
    AND ct.term = :term
    AND ct.deletedAt IS NULL
    AND ct.aClass.deletedAt IS NULL
    """)
    Optional<ClassTerm> findByAClassIdAndTerm(@Param("classId") Integer classId, @Param("term") ClassTerm.Term term);
}
