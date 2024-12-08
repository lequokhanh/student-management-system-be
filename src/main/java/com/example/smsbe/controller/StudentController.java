package com.example.smsbe.controller;

import com.example.smsbe.dto.StudentDTO;
import com.example.smsbe.request.AddStudentRequest;
import com.example.smsbe.request.PaginationParam;
import com.example.smsbe.response.PageWrapper;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseWrapper<PageWrapper<StudentDTO>> getStudents(PaginationParam param) {
        return new ResponseWrapper<PageWrapper<StudentDTO>>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(PageWrapper.of(studentService.getStudents(param)));
    }

    @GetMapping("/{id}")
    public ResponseWrapper<StudentDTO> getStudentById(@PathVariable("id") int id) {
        return new ResponseWrapper<StudentDTO>()
                .setStatusCode(200)
                .setMessage("Success")
                .setData(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseWrapper<StudentDTO> addStudent(@RequestBody AddStudentRequest req) {
        return new ResponseWrapper<StudentDTO>()
                .setStatusCode(201)
                .setMessage("Student added successfully")
                .setData(studentService.addStudent(req));
    }

    @PutMapping("/{id}")
    public ResponseWrapper<StudentDTO> updateStudent(@PathVariable("id") int id, @RequestBody AddStudentRequest req) {
        return new ResponseWrapper<StudentDTO>()
                .setStatusCode(200)
                .setMessage("Student updated successfully")
                .setData(studentService.updateStudent(id, req));
    }
}
