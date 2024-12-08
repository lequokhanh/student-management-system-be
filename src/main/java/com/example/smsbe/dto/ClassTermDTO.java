package com.example.smsbe.dto;

import com.example.smsbe.entity.Class;
import com.example.smsbe.entity.ClassTerm;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ClassTermDTO {
    private Integer id;
    @JsonAlias("class")
    private Class aClass;
    List<ClassDetailDTO> classDetail;
    private ClassTerm.Term term;
}
