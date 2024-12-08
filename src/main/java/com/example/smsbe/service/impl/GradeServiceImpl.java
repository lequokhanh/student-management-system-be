package com.example.smsbe.service.impl;

import com.example.smsbe.dto.GradeDTO;
import com.example.smsbe.repository.GradeRepository;
import com.example.smsbe.service.GradeService;
import com.example.smsbe.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public List<GradeDTO> getAllGrades() {
        return gradeRepository.findAllByDeletedAtIsNull().stream()
                .map(item -> MapperUtil.mapObject(item, GradeDTO.class))
                .collect(Collectors.toList());
    }

}
