package com.example.smsbe.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSchoolYearRequest {
    String year;
    boolean isCurrent;
}
