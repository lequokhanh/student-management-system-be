package com.example.smsbe.service;

import com.example.smsbe.dto.ClassTermDTO;

public interface ClassService {
    ClassTermDTO getClassDetail(Integer classId, Integer term);
    ClassTermDTO cloneListClassTerm(Integer sourceClassId, Integer sourceTerm, Integer targetClassId, Integer targetTerm, Boolean isOverride);
    ClassTermDTO addStudentToClass(Integer classId, Integer term, Integer studentId);
    ClassTermDTO updateStudentStatus(Integer classDetailId, Boolean isAvailable);
}
