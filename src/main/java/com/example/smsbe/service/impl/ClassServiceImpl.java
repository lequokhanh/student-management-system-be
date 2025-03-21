package com.example.smsbe.service.impl;

import com.example.smsbe.dto.ClassDTO;
import com.example.smsbe.dto.ClassDetailDTO;
import com.example.smsbe.dto.ClassTermDTO;
import com.example.smsbe.entity.Class;
import com.example.smsbe.entity.ClassDetail;
import com.example.smsbe.entity.ClassTerm;
import com.example.smsbe.entity.Student;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.ClassDetailRepository;
import com.example.smsbe.repository.ClassRepository;
import com.example.smsbe.repository.ClassTermRepository;
import com.example.smsbe.repository.StudentRepository;
import com.example.smsbe.service.ClassService;
import com.example.smsbe.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ClassTermRepository classTermRepository;
    private final ClassDetailRepository classDetailRepository;
    private final StudentRepository studentRepository;

    private ClassTerm.Term getTerm(Integer term) {
        return switch (term) {
            case 1 -> ClassTerm.Term._1;
            case 2 -> ClassTerm.Term._2;
            default -> throw new AppException(400, "Invalid term");
        };
    }

    public ClassTermDTO getClassDetail(Integer classId, Integer term) {
        Class aClass = classRepository.findById(classId).orElseThrow(() ->
                new AppException(404, "Class not found")
        );
        ClassTerm classTerm = classTermRepository.findByAClassIdAndTerm(classId, getTerm(term)).orElseThrow(() ->
                new AppException(404, "Term not found")
        );
        return new ClassTermDTO()
                .setId(classTerm.getId())
                .setAClass(MapperUtil.mapObject(aClass, ClassDTO.class))
                .setClassDetail(
                        MapperUtil.mapList(
                                classDetailRepository.findByClassTermId(classTerm.getId()),
                                ClassDetailDTO.class
                        )
                );
    }

    public ClassTermDTO cloneListClassTerm(Integer sourceClassId, Integer sourceTerm, Integer targetClassId, Integer targetTerm, Boolean isOverride) {
        if (sourceClassId.equals(targetClassId) && sourceTerm.equals(targetTerm)) {
            throw new AppException(400, "Source and target class are the same");
        }
        ClassTerm sourceClassTerm = classTermRepository.findByAClassIdAndTerm(sourceClassId, getTerm(sourceTerm))
                .orElseThrow(() -> new AppException(404, "Source term not found"));
        Class targetClass = classRepository.findById(targetClassId)
                .orElseThrow(() -> new AppException(404, "Target class not found"));
        ClassTerm targetClassTerm = classTermRepository.findByAClassIdAndTerm(targetClassId, getTerm(targetTerm))
                .orElseGet(() -> classTermRepository.save(new ClassTerm().setAClass(targetClass).setTerm(getTerm(targetTerm))));
        if (isOverride) {
            classDetailRepository.deleteByClassTermId(targetClassTerm.getId());
        }
        List<ClassDetail> sourceClassDetails = classDetailRepository.findByClassTermId(sourceClassTerm.getId());
        for (ClassDetail sourceDetail : sourceClassDetails) {
            ClassDetail targetDetail = new ClassDetail()
                    .setClassTerm(targetClassTerm)
                    .setStudent(sourceDetail.getStudent())
                    .setIsAvailable(sourceDetail.getIsAvailable());
            try {
                classDetailRepository.save(targetDetail);
            } catch (Exception ignored) {
            }
        }
        return getClassDetail(targetClassId, targetTerm);
    }


    public ClassTermDTO addStudentToClass(Integer classId, Integer term, Integer studentId) {
        Class aClass = classRepository.findById(classId).orElseThrow(() ->
                new AppException(404, "Class not found")
        );
        ClassTerm classTerm = classTermRepository.findByAClassIdAndTerm(classId, getTerm(term)).orElseThrow(() ->
                new AppException(404, "Term not found")
        );
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new AppException(404, "Student not found")
        );
        if (student.getDeletedAt() != null) {
            throw new AppException(404, "Student not found");
        }
        if (student.getStatus() == Student.Status.INACTIVE) {
            throw new AppException(400, "Student is inactive");
        }
        // Check if the student is already enrolled in a class for the same term and school year
        ClassDetail existingClassDetail = classDetailRepository.findExistingEnrollment(
                studentId,
                getTerm(term),
                aClass.getSchoolYear().getId()
        );

        if (existingClassDetail != null) {
            String existingClassName = existingClassDetail.getClassTerm().getAClass().getName();
            throw new AppException(400, "Student" + studentId + " - " +student.getName() + " is already enrolled in class '" + existingClassName +
                    "' for this term and school year.");
        }
        if (aClass.getTotal() <= classDetailRepository.countByClassTermId(classTerm.getId())) {
            throw new AppException(400, "Class is full");
        }
        ClassDetail cd = classDetailRepository.findByClassTermIdAndStudentId(classTerm.getId(), studentId).orElse(null);
        if (cd != null && cd.getDeletedAt() == null) {
            throw new AppException(400, "Student" + studentId + " - " +student.getName() + " already in class");
        } else if (cd != null) {
            cd.setDeletedAt(null);
            classDetailRepository.save(cd);
            return getClassDetail(classId, term);
        }
        ClassDetail classDetail = new ClassDetail()
                .setClassTerm(classTerm)
                .setStudent(student)
                .setIsAvailable(true);
        classDetailRepository.save(classDetail);

        return getClassDetail(classId, term);
    }

    public ClassTermDTO deleteStudent(Integer classDetailId) {
        ClassDetail classDetail = classDetailRepository.findById(classDetailId)
                .orElseThrow(() -> new AppException(404, "Class detail not found"));
        classDetail.setDeletedAt(new Date());
        classDetailRepository.save(classDetail);
        return getClassDetail(classDetail.getClassTerm().getAClass().getId(), classDetail.getClassTerm().getTerm().ordinal() + 1);
    }
}
