package com.example.smsbe.dto;

import com.example.smsbe.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ClassDetailDTO {
    private Integer id;
    private StudentDTO student;
    private Boolean isAvailable;
}
