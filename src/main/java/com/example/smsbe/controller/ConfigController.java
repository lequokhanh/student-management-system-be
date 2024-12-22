package com.example.smsbe.controller;

import com.example.smsbe.dto.GradeDTO;
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

    @GetMapping("/minage")
    public ResponseWrapper<Integer> getMinAge() {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getMinAge());
    }

    @PutMapping("/minage")
    public ResponseWrapper<Integer> updateMinAge(@RequestBody Integer req) {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateMinAge(req));
    }

    @GetMapping("/maxage")
    public ResponseWrapper<Integer> getMaxAge() {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getMaxAge());
    }

    @PutMapping("/maxage")
    public ResponseWrapper<Integer> updateMaxAge(@RequestBody Integer req) {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateMaxAge(req));
    }

    @GetMapping("/minscorepass")
    public ResponseWrapper<Integer> getMinScorePass() {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getMinScorePass());
    }

    @PutMapping("/minscorepass")
    public ResponseWrapper<Integer> updateMinScorePass(@RequestBody Integer req) {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateMinScorePass(req));
    }

    @GetMapping("/maxtotal")
    public ResponseWrapper<Integer> getMaxTotal() {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getMaxTotal());
    }

    @PutMapping("/maxtotal")
    public ResponseWrapper<Integer> updateMaxTotal(@RequestBody Integer req) {
        return new ResponseWrapper<Integer>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateMaxTotal(req));
    }

    @GetMapping("/grade")
    public ResponseWrapper<List<GradeDTO>> getGrades() {
        return new ResponseWrapper<List<GradeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.getGrades());
    }

    @PostMapping("/grade")
    public ResponseWrapper<List<GradeDTO>> addGrade(@RequestBody Integer req) {
        return new ResponseWrapper<List<GradeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.addGrade(req));
    }

    @PutMapping("/grade/{id}")
    public ResponseWrapper<List<GradeDTO>> updateGrade(@PathVariable("id") Integer id, @RequestBody Integer req) {
        return new ResponseWrapper<List<GradeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.updateGrade(id, req));
    }

    @DeleteMapping("/grade/{id}")
    public ResponseWrapper<List<GradeDTO>> deleteGrade(@PathVariable("id") Integer id) {
        return new ResponseWrapper<List<GradeDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(configService.deleteGrade(id));
    }

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
