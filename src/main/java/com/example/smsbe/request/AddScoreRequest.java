package com.example.smsbe.request;

import lombok.Getter;

@Getter
public class AddScoreRequest {
    private String subjectId;
    private Integer typeId;
    private Double score;
    private Integer ClassDetailId;
}
