package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
@Getter
@Setter
@Accessors(chain = true)
public class SchoolYearDetailDTO extends SchoolYearDTO {
    List<ClassDTO> classes;
}
