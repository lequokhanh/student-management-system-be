package com.example.smsbe.service;

import com.example.smsbe.dto.GradeDTO;
import com.example.smsbe.dto.ScoreTypeDTO;
import com.example.smsbe.dto.SubjectDTO;
import com.example.smsbe.request.AddScoreTypeRequest;
import com.example.smsbe.request.UpdateScoreTypeRequest;
import com.example.smsbe.request.UpdateSubjectRequest;

import java.util.List;

public interface ConfigService {

    List<ScoreTypeDTO> getScoreTypes();
    List<ScoreTypeDTO> addScoreType(AddScoreTypeRequest req);
    List<ScoreTypeDTO> updateScoreType(Integer id, UpdateScoreTypeRequest req);
    List<ScoreTypeDTO> deleteScoreType(Integer id);
    List<SubjectDTO> getSubjects();
    List<SubjectDTO> addSubject(SubjectDTO subjectDTO);
    List<SubjectDTO> updateSubject(String id, UpdateSubjectRequest req);
    List<SubjectDTO> deleteSubject(String id);
    Integer getMinAge();
    Integer updateMinAge(Integer req);
    Integer getMaxAge();
    Integer updateMaxAge(Integer req);
    Integer getMinScorePass();
    Integer updateMinScorePass(Integer req);
    Integer getMaxTotal();
    Integer updateMaxTotal(Integer req);
    List<GradeDTO> getGrades();
    List<GradeDTO> addGrade(Integer req);
    List<GradeDTO> updateGrade(Integer id, Integer req);
    List<GradeDTO> deleteGrade(Integer id);
}
