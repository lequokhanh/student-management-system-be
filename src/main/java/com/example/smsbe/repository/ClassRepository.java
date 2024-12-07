package com.example.smsbe.repository;

import com.example.smsbe.entity.Class;
import com.example.smsbe.entity.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    @Query("""
    SELECT c
    FROM Class c
    WHERE c.schoolYear = :schoolYear
    AND c.deletedAt IS NULL
    """)
    List<Class> findBySchoolYear(SchoolYear schoolYear);

    @Query("""
    SELECT c
    FROM Class c
    WHERE c.id = :id
    AND c.deletedAt IS NULL
    """)
    Optional<Class> findById(@Param("id") Integer id);

    @Query("""
    SELECT c
    FROM Class c
    WHERE c.name = :name
    AND c.schoolYear = :schoolYear
    """)
    Optional<Class> findByNameAndSchoolYear(@Param("name") String name,
                                            @Param("schoolYear") SchoolYear schoolYear);
}
