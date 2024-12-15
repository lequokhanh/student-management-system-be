package com.example.smsbe.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddScoreRequest {
    private String subjectId;
    private Integer typeId;
    private Double score;
    private Integer ClassDetailId;
}
