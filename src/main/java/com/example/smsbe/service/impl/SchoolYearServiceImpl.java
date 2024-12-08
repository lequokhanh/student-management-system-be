package com.example.smsbe.service.impl;

import com.example.smsbe.dto.ClassDTO;
import com.example.smsbe.dto.GradeDetailDTO;
import com.example.smsbe.dto.SchoolYearDTO;
import com.example.smsbe.dto.SchoolYearDetailDTO;
import com.example.smsbe.entity.Class;
import com.example.smsbe.entity.ClassTerm;
import com.example.smsbe.entity.SchoolYear;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.ClassRepository;
import com.example.smsbe.repository.ClassTermRepository;
import com.example.smsbe.repository.GradeRepository;
import com.example.smsbe.repository.SchoolYearRepository;
import com.example.smsbe.request.AddClassRequest;
import com.example.smsbe.request.AddSchoolYearRequest;
import com.example.smsbe.request.UpdateClassRequest;
import com.example.smsbe.service.SchoolYearService;
import com.example.smsbe.util.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolYearServiceImpl implements SchoolYearService {
    private final SchoolYearRepository schoolYearRepository;
    private final ClassRepository classRepository;
    private final GradeRepository gradeRepository;
    private final ClassTermRepository classTermRepository;

    public List<SchoolYearDTO> findAll() {
        return schoolYearRepository.findAll().stream()
                .map(item -> MapperUtil.mapObject(item, SchoolYearDTO.class))
                .toList();
    }

    public SchoolYearDetailDTO findById(Integer id) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "School year not found"));
        SchoolYearDetailDTO schoolYearDetailDTO = MapperUtil.mapObject(schoolYear, SchoolYearDetailDTO.class);
        List<Class> classList = classRepository.findBySchoolYear(schoolYear);
        Map<Integer, List<Class>> groupedByGrade = classList.stream()
                .collect(Collectors.groupingBy(cls -> cls.getGrade().getId()));
        List<GradeDetailDTO> gradeDetailDTOs = groupedByGrade.entrySet().stream()
                .map(entry -> {
                    Integer gradeId = entry.getKey();
                    List<Class> classesInGrade = entry.getValue();
                    List<ClassDTO> classDTOs = MapperUtil.mapList(classesInGrade, ClassDTO.class);
                    GradeDetailDTO gradeDetailDTO = new GradeDetailDTO();
                    gradeDetailDTO.setId(gradeId);
                    gradeDetailDTO.setGrade(classesInGrade.get(0).getGrade().getGrade());
                    gradeDetailDTO.setClasses(classDTOs);
                    return gradeDetailDTO;
                })
                .collect(Collectors.toList());
        schoolYearDetailDTO.setGrades(gradeDetailDTOs);
        return schoolYearDetailDTO;
    }

    @Transactional
    public SchoolYearDTO save(AddSchoolYearRequest req) {
        SchoolYear schoolYear = MapperUtil.mapObject(req, SchoolYear.class);
        if (schoolYear.isCurrent()) {
            schoolYearRepository.findByIsCurrentTrue()
                    .ifPresent(item -> {
                        item.setCurrent(false);
                        schoolYearRepository.save(item);
                    });
        }
        return MapperUtil.mapObject(schoolYearRepository.save(schoolYear), SchoolYearDTO.class);
    }

    public SchoolYearDTO update(Integer id, AddSchoolYearRequest req) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "School year not found"));
        if (req.isCurrent()) {
            schoolYearRepository.findByIsCurrentTrue()
                    .ifPresent(item -> {
                        item.setCurrent(false);
                        schoolYearRepository.save(item);
                    });
        }
        return MapperUtil.mapObject(
                schoolYearRepository.save(
                        MapperUtil.mapObject(req, SchoolYear.class)
                                .setId(schoolYear.getId())
                ), SchoolYearDTO.class
        );
    }

    @Transactional
    public SchoolYearDetailDTO addClass(Integer id, AddClassRequest req) {
        Class newClass = MapperUtil.mapObject(req, Class.class);
        if (req.getGrade() == null) {
            throw new AppException(409, "Grade is required");
        }
        newClass.setGrade(gradeRepository.findByGrade(req.getGrade())
                .orElseThrow(() -> new AppException(404, "Grade not found")));
        newClass.setSchoolYear(schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "School year not found")));
        Class existClass = classRepository.findByNameAndSchoolYear(newClass.getName(), newClass.getSchoolYear())
                .orElse(null);
        if (existClass != null && existClass.getDeletedAt() == null) {
            throw new AppException(409, "Class already exists");
        }
        if (existClass != null) {
            newClass.setId(existClass.getId());
        }
        newClass = classRepository.save(newClass);
        for (ClassTerm.Term term : ClassTerm.Term.values()) {
            ClassTerm classTerm = new ClassTerm();
            classTerm.setAClass(newClass);
            classTerm.setTerm(term);
            classTermRepository.save(classTerm);
        }
        return MapperUtil.mapObject(findById(id), SchoolYearDetailDTO.class);
    }

    public SchoolYearDetailDTO updateClass(Integer id, Integer classId, UpdateClassRequest req) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "School year not found"));
        Class newClass = classRepository.findById(classId)
                .orElseThrow(() -> new AppException(404, "Class not found"));
        if (!newClass.getSchoolYear().getId().equals(schoolYear.getId())) {
            throw new AppException(404, "Class not found in school year");
        }
        newClass.setName(req.getName());
        newClass.setTotal(req.getTotal());
        classRepository.save(newClass);
        return MapperUtil.mapObject(findById(id), SchoolYearDetailDTO.class);
    }

    @Transactional
    public SchoolYearDetailDTO removeClass(Integer id, Integer classId) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "School year not found"));
        Class newClass = classRepository.findById(classId)
                .orElseThrow(() -> new AppException(404, "Class not found"));
        if (!newClass.getSchoolYear().getId().equals(schoolYear.getId())) {
            throw new AppException(404, "Class not found in school year");
        }
        newClass.setDeletedAt(new Date());
        classRepository.save(newClass);
        return MapperUtil.mapObject(findById(id), SchoolYearDetailDTO.class);
    }
}
