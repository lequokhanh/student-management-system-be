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
    private Integer id;
    private SubjectDTO subject;
    private ClassDetailDTO classDetail;
    Map<ScoreTypeDTO, List<Double>> scores;
    private Double avgScore;
}
