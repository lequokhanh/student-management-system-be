package com.example.smsbe.service.impl;

import com.example.smsbe.dto.ClassDTO;
import com.example.smsbe.dto.SchoolYearDTO;
import com.example.smsbe.dto.SchoolYearDetailDTO;
import com.example.smsbe.entity.Class;
import com.example.smsbe.entity.SchoolYear;
import com.example.smsbe.repository.ClassRepository;
import com.example.smsbe.repository.SchoolYearRepository;
import com.example.smsbe.request.AddClassRequest;
import com.example.smsbe.request.AddSchoolYearRequest;
import com.example.smsbe.service.SchoolYearService;
import com.example.smsbe.util.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolYearServiceImpl implements SchoolYearService {
    private final SchoolYearRepository schoolYearRepository;
    private final ClassRepository classRepository;

    public List<SchoolYearDTO> findAll() {
        return schoolYearRepository.findAll().stream()
                .map(item -> MapperUtil.mapObject(item, SchoolYearDTO.class))
                .toList();
    }

    public SchoolYearDetailDTO findById(Integer id) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School year not found"));
        SchoolYearDetailDTO schoolYearDetailDTO = MapperUtil.mapObject(schoolYear, SchoolYearDetailDTO.class);
        schoolYearDetailDTO.setClasses(
                MapperUtil.mapList(classRepository.findBySchoolYear(schoolYear), ClassDTO.class));
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
                .orElseThrow(() -> new RuntimeException("School year not found"));
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
        newClass.setSchoolYear(schoolYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School year not found")));
        Class existClass = classRepository.findByNameAndSchoolYear(newClass.getName(), newClass.getSchoolYear())
                .orElse(null);
        if (existClass != null && existClass.getDeletedAt() == null) {
            throw new RuntimeException("Class already exists");
        }
        if (existClass != null) {
            newClass.setId(existClass.getId());
        }
        classRepository.save(newClass);
        return MapperUtil.mapObject(findById(id), SchoolYearDetailDTO.class);
    }
    @Transactional
    public SchoolYearDetailDTO removeClass(Integer id, Integer classId) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School year not found"));
        Class newClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        if (!newClass.getSchoolYear().getId().equals(schoolYear.getId())) {
            throw new RuntimeException("Class not found in school year");
        }
        newClass.setDeletedAt(new Date());
        classRepository.save(newClass);
        return MapperUtil.mapObject(findById(id), SchoolYearDetailDTO.class);
    }
}
