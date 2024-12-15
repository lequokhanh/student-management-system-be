package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassTerm;
import com.example.smsbe.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("""
    SELECT s
    FROM Student s
    WHERE s.status = 'ACTIVE'
    AND s.deletedAt IS NULL
    AND (
        :keyword IS NULL OR
        LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(CONCAT('', s.id)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    """)
    Page<Student> getAllStudents(Pageable pageable, @Param("keyword") String keyword);

}
