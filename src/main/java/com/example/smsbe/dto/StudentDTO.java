package com.example.smsbe.dto;

import com.example.smsbe.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class StudentDTO {
    private Integer id;
    private String name;
    private Student.Gender gender;
    private Date dob;
    private String email;
    private String address;
    private Student.Status status;
}
