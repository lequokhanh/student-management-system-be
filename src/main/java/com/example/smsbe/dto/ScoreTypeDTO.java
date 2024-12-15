package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ScoreTypeDTO {
    private Integer id;
    private String type;
    private Double weight;
}
