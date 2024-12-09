package com.example.smsbe.dto;

import com.example.smsbe.entity.ClassTerm;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ClassTermDTO {
    private Integer id;
    private ClassDTO aClass;
    List<ClassDetailDTO> classDetail;
    private ClassTerm.Term term;
}
