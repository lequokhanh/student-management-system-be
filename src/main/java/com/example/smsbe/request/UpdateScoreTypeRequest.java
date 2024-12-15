package com.example.smsbe.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScoreTypeRequest {
    private String type;
    private Double weight;
}
