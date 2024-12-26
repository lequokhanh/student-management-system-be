package com.example.smsbe.controller;

import com.example.smsbe.dto.GradeDTO;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/grade")
@RequiredArgsConstructor
public class GradeController {
    private final ConfigService configService;
    @GetMapping
    public ResponseWrapper<List<GradeDTO>> getAllGrades() {
        return new ResponseWrapper<List<GradeDTO>>()
                .setStatusCode(200)
                .setMessage("Get all grades successfully")
                .setData(configService.getGrades());
    }
}
