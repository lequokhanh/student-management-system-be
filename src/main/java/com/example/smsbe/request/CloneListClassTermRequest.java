package com.example.smsbe.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CloneListClassTermRequest {
    private Integer sourceClassId;
    private Integer sourceTerm;
    private Integer targetClassId;
    private Integer targetTerm;
}
