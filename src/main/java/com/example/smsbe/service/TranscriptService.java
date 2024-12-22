package com.example.smsbe.service;

import com.example.smsbe.dto.ScoreTypeDTO;
import com.example.smsbe.dto.SubjectDTO;
import com.example.smsbe.dto.TranscriptDTO;
import com.example.smsbe.request.AddScoreRequest;
import com.example.smsbe.request.UpdateScoreTypeRequest;
import com.example.smsbe.request.UpdateSubjectRequest;
import com.example.smsbe.response.TranscriptSummaryResponse;

import java.util.List;

public interface TranscriptService {
    void addScore(AddScoreRequest req);
    void deleteScore(Integer transcriptId);
    List<TranscriptDTO> getTranscriptByClassTermAndSubject(Integer classId, Integer term, String subjectId);
    List<TranscriptSummaryResponse> getTranscriptSummaryByClassTermAndSubject(Integer schoolYearID, Integer term, String subjectId);
    List<TranscriptSummaryResponse> getTranscriptSummaryByClassTerm(Integer schoolYearID, Integer term);
}
