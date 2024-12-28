package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassTerm;
import com.example.smsbe.entity.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {
    @Query("""
           SELECT t
           FROM Transcript t
           WHERE t.deletedAt IS NULL
             AND t.classDetail.classTerm.aClass.id = :classId
             AND t.classDetail.classTerm.term = :term
             AND t.subject.id = :subjectId
           ORDER BY t.id
           """)
    List<Transcript> findByClassTermAndSubject(@Param("classId") Integer classId,
                                               @Param("term") ClassTerm.Term term,
                                               @Param("subjectId") String subjectId);

    @Query("""
      SELECT t
      FROM Transcript t
      WHERE t.deletedAt IS NULL
         AND t.classDetail.classTerm.aClass.id = :class
            AND t.classDetail.classTerm.term = :term
    """)
    List<Transcript> findByClassTerm(@Param("class") Integer classId,
                                       @Param("term") ClassTerm.Term term);
}
