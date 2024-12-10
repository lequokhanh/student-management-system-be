package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Integer> {
    @Query("""
    SELECT cd
    FROM ClassDetail cd
    WHERE cd.classTerm.id = :classTermId
    AND cd.deletedAt IS NULL
    """)
    List<ClassDetail> findByClassTermId(Integer classTermId);

    @Query("""
    SELECT COUNT(cd)
    FROM ClassDetail cd
    WHERE cd.classTerm.id = :id
    AND cd.deletedAt IS NULL
    """)
    Integer countByClassTermId(Integer id);

    void deleteByClassTermId(Integer classTermId);
}
