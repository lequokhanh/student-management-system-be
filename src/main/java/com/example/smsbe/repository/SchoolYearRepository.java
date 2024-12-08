package com.example.smsbe.repository;

import com.example.smsbe.entity.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer> {
    @Query("""
    SELECT sy
    FROM SchoolYear sy
    WHERE sy.deletedAt IS NULL
    """)
    List<SchoolYear> findAll();

    @Query("""
    SELECT sy
    FROM SchoolYear sy
    WHERE sy.id = :id
    AND sy.deletedAt IS NULL
    """)
    Optional<SchoolYear> findById(@Param("id") Integer id);

    @Query("""
    SELECT sy
    FROM SchoolYear sy
    WHERE sy.isCurrent = TRUE
    AND sy.deletedAt IS NULL
    """)
    Optional<SchoolYear> findByIsCurrentTrue();
}
