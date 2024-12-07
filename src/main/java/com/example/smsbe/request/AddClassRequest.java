package com.example.smsbe.request;

import com.example.smsbe.entity.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddClassRequest {
    private Grade grade;
    private String name;
    private Integer total;
}
