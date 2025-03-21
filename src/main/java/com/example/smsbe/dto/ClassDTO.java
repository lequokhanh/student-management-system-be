package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ClassDTO {
    private Integer id;
    private GradeDTO grade;
    private String name;
    private Integer total;
}
