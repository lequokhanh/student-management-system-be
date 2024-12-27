package com.example.smsbe.service.impl;

import com.example.smsbe.dto.StudentDTO;
import com.example.smsbe.entity.Student;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.ConfigRepository;
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

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ConfigRepository configRepository;

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

        LocalDate dob = student.getDob().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int minAge = Integer.parseInt(configRepository.findByConfigKey("minAge").orElseThrow(
                () -> new AppException(500, "Min age not found")).getValue());
        int maxAge = Integer.parseInt(configRepository.findByConfigKey("maxAge").orElseThrow(
                () -> new AppException(500, "Max age not found")).getValue());

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < minAge || age > maxAge) {
            throw new AppException(400, "Student age must be between " + minAge + " and " + maxAge);
        }

        return MapperUtil.mapObject(studentRepository.save(student), StudentDTO.class);
    }

    public StudentDTO updateStudent(int id, AddStudentRequest req) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new AppException(404, "Student not found");
        }
        Student updatedStudent = MapperUtil.mapObject(req, Student.class);
        Date dob = updatedStudent.getDob();
        int minAge = Integer.parseInt(configRepository.findByConfigKey("minAge").orElseThrow(
                () -> new AppException(500, "Min age not found")).getValue());
        int maxAge = Integer.parseInt(configRepository.findByConfigKey("maxAge").orElseThrow(
                () -> new AppException(500, "Max age not found")).getValue());
        int age = new Date().getYear() - dob.getYear();
        if (age < minAge || age > maxAge) {
            throw new AppException(400, "Student age must be between " + minAge + " and " + maxAge);
        }
        updatedStudent.setId(id);
        return MapperUtil.mapObject(studentRepository.save(updatedStudent), StudentDTO.class);
    }
}
