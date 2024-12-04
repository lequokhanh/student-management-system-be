package com.example.smsbe.request;

import com.example.smsbe.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
public class AddStudentRequest {
    private String name;
    private Student.Gender gender;
    private Date dob;
    private String email;
    private String address;
    private Student.Status status;
}
