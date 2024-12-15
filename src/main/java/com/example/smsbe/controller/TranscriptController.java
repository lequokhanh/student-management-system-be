package com.example.smsbe.controller;

import com.example.smsbe.dto.TranscriptDTO;
import com.example.smsbe.request.AddScoreRequest;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.response.TranscriptSummaryResponse;
import com.example.smsbe.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transcript")
@RequiredArgsConstructor
public class TranscriptController {
    private final TranscriptService transcriptService;

    @PostMapping
    public ResponseWrapper<Void> addScore(@RequestBody AddScoreRequest req) {
        transcriptService.addScore(req);
        return new ResponseWrapper<Void>()
                .setStatusCode(201)
                .setMessage("Success");
    }

    @GetMapping("/class/{classId}/term/{term}/subject/{subjectId}")
    public ResponseWrapper<List<TranscriptDTO>> getTranscript(@PathVariable("classId") Integer classId,
                                                              @PathVariable("term") Integer term,
                                                              @PathVariable("subjectId") String subjectId) {
        return new ResponseWrapper<List<TranscriptDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(transcriptService.getTranscriptByClassTermAndSubject(classId, term, subjectId));
    }

    @GetMapping("/schoolYear/{schoolYearId}/term/{term}/subject/{subjectId}")
    public ResponseWrapper<List<TranscriptSummaryResponse>> getTranscriptSummary(@PathVariable("schoolYearId") Integer schoolYearId,
                                                                                 @PathVariable("term") Integer term,
                                                                                 @PathVariable("subjectId") String subjectId) {
        return new ResponseWrapper<List<TranscriptSummaryResponse>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(transcriptService.getTranscriptSummaryByClassTermAndSubject(schoolYearId, term, subjectId));
    }

    @GetMapping("/class/{classId}/term/{term}")
    public ResponseWrapper<List<TranscriptSummaryResponse>> getTranscriptSummary(@PathVariable("classId") Integer classId,
                                                                                 @PathVariable("term") Integer term) {
        return new ResponseWrapper<List<TranscriptSummaryResponse>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(transcriptService.getTranscriptSummaryByClassTerm(classId, term));
    }
}
