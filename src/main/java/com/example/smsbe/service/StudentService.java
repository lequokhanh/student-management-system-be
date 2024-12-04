package com.example.smsbe.service;

import com.example.smsbe.dto.StudentDTO;
import com.example.smsbe.request.AddStudentRequest;
import com.example.smsbe.request.PaginationParam;
import org.springframework.data.domain.Page;

public interface StudentService {
    StudentDTO getStudentById(int id);
    Page<StudentDTO> getStudents(PaginationParam param);
    StudentDTO addStudent(AddStudentRequest req);
    StudentDTO updateStudent(int id, AddStudentRequest req);
}
