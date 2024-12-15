package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SubjectDTO {
    private String id;
    private String subject;
    private Double efficient;
}
