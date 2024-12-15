package com.example.smsbe.response;

import com.example.smsbe.dto.ClassTermDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TranscriptSummaryResponse {
    ClassTermDTO classTerm;
    Integer total;
    Integer passed;
    Double rate;
}
