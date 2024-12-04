package com.example.smsbe.service.impl;

import com.example.smsbe.dto.StudentDTO;
import com.example.smsbe.entity.Student;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.StudentRepository;
import com.example.smsbe.request.AddStudentRequest;
import com.example.smsbe.request.PaginationParam;
import com.example.smsbe.service.StudentService;
import com.example.smsbe.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentDTO getStudentById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new AppException(404, "Student not found");
        }
        return MapperUtil.mapObject(student.get(), StudentDTO.class);
    }

    public Page<StudentDTO> getStudents(PaginationParam param) {
        Pageable pageable = PageRequest.of(param.getPage(), param.getSize());
        return studentRepository.getAllStudents(pageable, param.getKeyword())
                .map(student -> MapperUtil.mapObject(student, StudentDTO.class));
    }

    public StudentDTO addStudent(AddStudentRequest req) {
        Student student = MapperUtil.mapObject(req, Student.class);
        return MapperUtil.mapObject(studentRepository.save(student), StudentDTO.class);
    }

    public StudentDTO updateStudent(int id, AddStudentRequest req) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new AppException(404, "Student not found");
        }
        Student updatedStudent = MapperUtil.mapObject(req, Student.class);
        updatedStudent.setId(id);
        return MapperUtil.mapObject(studentRepository.save(updatedStudent), StudentDTO.class);
    }
}
