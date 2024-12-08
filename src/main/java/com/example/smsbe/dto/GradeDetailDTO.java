package com.example.smsbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
@Getter
@Setter
@Accessors(chain = true)
public class GradeDetailDTO extends GradeDTO {
    List<ClassDTO> classes;
}
