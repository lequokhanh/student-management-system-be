package com.example.smsbe.controller;

import com.example.smsbe.dto.ScoreTypeDTO;
import com.example.smsbe.dto.SubjectDTO;
import com.example.smsbe.request.AddScoreTypeRequest;
import com.example.smsbe.request.UpdateScoreTypeRequest;
import com.example.smsbe.request.UpdateSubjectRequest;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/config")
@RequiredArgsConstructor
public class ConfigController {
    private final ConfigService configService;

    @GetMapping("/subject")
    public ResponseWrapper<List<SubjectDTO>> getSubjects() {
        return new ResponseWrapper<List<SubjectDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getSubjects());
    }

    @PostMapping("/subject")
    public ResponseWrapper<List<SubjectDTO>> addSubject(@RequestBody SubjectDTO req) {
        return new ResponseWrapper<List<SubjectDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.addSubject(req));
    }

    @PutMapping("/subject/{id}")
    public ResponseWrapper<List<SubjectDTO>> updateSubject(@PathVariable("id") String id, @RequestBody UpdateSubjectRequest req) {
        return new ResponseWrapper<List<SubjectDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateSubject(id, req));
    }

    @DeleteMapping("/subject/{id}")
    public ResponseWrapper<List<SubjectDTO>> deleteSubject(@PathVariable("id") String id) {
        return new ResponseWrapper<List<SubjectDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.deleteSubject(id));
    }

    @GetMapping("/scoreType")
    public ResponseWrapper<List<ScoreTypeDTO>> getScoreTypes() {
        return new ResponseWrapper<List<ScoreTypeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getScoreTypes());
    }

    @PostMapping("/scoreType")
    public ResponseWrapper<List<ScoreTypeDTO>> addScoreType(@RequestBody AddScoreTypeRequest req) {
        return new ResponseWrapper<List<ScoreTypeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.addScoreType(req));
    }

    @PutMapping("/scoreType/{id}")
    public ResponseWrapper<List<ScoreTypeDTO>> updateScoreType(@PathVariable("id") Integer id, @RequestBody UpdateScoreTypeRequest req) {
        return new ResponseWrapper<List<ScoreTypeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateScoreType(id, req));
    }

    @DeleteMapping("/scoreType/{id}")
    public ResponseWrapper<List<ScoreTypeDTO>> deleteScoreType(@PathVariable("id") Integer id) {
        return new ResponseWrapper<List<ScoreTypeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.deleteScoreType(id));
    }
}
