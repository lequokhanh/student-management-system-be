package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Integer> {
    @Query("""
    SELECT cd
    FROM ClassDetail cd
    WHERE cd.classTerm.id = :classTermId
    AND cd.deletedAt IS NULL
    """)
    List<ClassDetail> findByClassTermId(Integer classTermId);
}
