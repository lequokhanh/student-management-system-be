package com.example.smsbe.controller;

import com.example.smsbe.dto.ClassTermDTO;
import com.example.smsbe.request.CloneListClassTermRequest;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @GetMapping("/{classId}/terms/{term}")
    public ResponseWrapper<ClassTermDTO> getClassDetail(@PathVariable Integer classId, @PathVariable Integer term) {
        return new ResponseWrapper<ClassTermDTO>()
                .setData(classService.getClassDetail(classId, term))
                .setMessage("Class detail retrieved successfully")
                .setStatusCode(200);
    }

    @PostMapping("/clone")
    public ResponseWrapper<ClassTermDTO> cloneListClassTerm(@RequestBody CloneListClassTermRequest request) {
        return new ResponseWrapper<ClassTermDTO>()
                .setData(classService.cloneListClassTerm(
                        request.getSourceClassId(),
                        request.getSourceTerm(),
                        request.getTargetClassId(),
                        request.getTargetTerm(),
                        request.getIsOverride()
                ))
                .setMessage("Class term cloned successfully")
                .setStatusCode(201);
    }

    @PostMapping("/{classId}/terms/{term}/students/{studentId}")
    public ResponseWrapper<ClassTermDTO> addStudentToClass(@PathVariable Integer classId,
                                                           @PathVariable Integer term,
                                                           @PathVariable Integer studentId) {
        return new ResponseWrapper<ClassTermDTO>()
                .setData(classService.addStudentToClass(classId, term, studentId))
                .setMessage("Student added to class successfully")
                .setStatusCode(201);
    }

    @DeleteMapping("/classDetails/{classDetailId}")
    public ResponseWrapper<ClassTermDTO> updateStudentStatus(
            @PathVariable Integer classDetailId) {
        return new ResponseWrapper<ClassTermDTO>()
                .setData(classService.deleteStudent(classDetailId))
                .setMessage("Student status updated successfully")
                .setStatusCode(200);
    }
}
