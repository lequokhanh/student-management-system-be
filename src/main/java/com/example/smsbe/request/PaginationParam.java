package com.example.smsbe.request;

import lombok.Getter;

@Getter
public class PaginationParam {
    int page;
    int size;
    String keyword;
}
