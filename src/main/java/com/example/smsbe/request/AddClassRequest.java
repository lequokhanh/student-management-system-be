package com.example.smsbe.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddClassRequest {
    private Integer grade;
    private String name;
    private Integer total;
}
