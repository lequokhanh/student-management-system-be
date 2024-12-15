package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class TranscriptDTO {
    private SubjectDTO subject;
    private ClassDetailDTO classDetail;
    Map<Integer, List<ScoreDTO>> scores;
    private Double avgScore;
}
