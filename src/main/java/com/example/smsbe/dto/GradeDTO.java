package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GradeDTO {
    private Integer id;
    private Integer grade;
}
