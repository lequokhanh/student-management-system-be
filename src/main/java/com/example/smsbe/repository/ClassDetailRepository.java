package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassDetail;
import com.example.smsbe.entity.ClassTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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

    @Query("""
    SELECT COUNT(cd)
    FROM ClassDetail cd
    WHERE cd.classTerm.id = :id
    AND cd.deletedAt IS NULL
    """)
    Integer countByClassTermId(Integer id);

    void deleteByClassTermId(Integer classTermId);

    @Query("""
    SELECT cd
    FROM ClassDetail cd
    WHERE cd.classTerm.term = :term
    AND cd.classTerm.aClass.id = :classId
    AND cd.deletedAt IS NULL
    """)
    List<ClassDetail> findByClassTerm(Integer classId, ClassTerm.Term term);

    @Query("""
    SELECT cd
    FROM ClassDetail cd
    WHERE cd.id = :id
    AND cd.deletedAt IS NULL
    """)
    Optional<ClassDetail> findById(@Param("id") Integer id);

    @Query("""
    SELECT cd
    FROM ClassDetail cd
    WHERE cd.classTerm.id = :id
    AND cd.student.id = :studentId
    """)
    Optional<ClassDetail> findByClassTermIdAndStudentId(Integer id, Integer studentId);

    @Query("SELECT CASE WHEN COUNT(cd) > 0 THEN true ELSE false END " +
            "FROM ClassDetail cd " +
            "JOIN cd.classTerm ct " +
            "JOIN ct.aClass c " +
            "WHERE cd.student.id = :studentId " +
            "AND ct.term = :term " +
            "AND c.schoolYear.id = :schoolYearId")
    boolean existsByStudentIdAndTermAndSchoolYear(@Param("studentId") Integer studentId,
                                                  @Param("term") ClassTerm.Term term,
                                                  @Param("schoolYearId") Integer schoolYearId);

}
