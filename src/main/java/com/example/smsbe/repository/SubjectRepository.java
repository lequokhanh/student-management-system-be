package com.example.smsbe.repository;

import com.example.smsbe.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    @Query("SELECT s FROM Subject s WHERE s.deletedAt IS NULL")
    List<Subject> findAllActive();

    @Query("SELECT s FROM Subject s WHERE s.id = :id AND s.deletedAt IS NULL")
    Optional<Subject> findByIdAndDeletedAtIsNull(@Param("id") String id);
}
