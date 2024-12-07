package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SchoolYearDTO {
    private Integer id;
    private String year;
    private boolean isCurrent;
}
