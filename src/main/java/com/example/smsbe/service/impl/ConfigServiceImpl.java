package com.example.smsbe.service.impl;

import com.example.smsbe.dto.ScoreTypeDTO;
import com.example.smsbe.dto.SubjectDTO;
import com.example.smsbe.entity.GlobalConfig;
import com.example.smsbe.entity.ScoreType;
import com.example.smsbe.entity.Subject;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.ConfigRepository;
import com.example.smsbe.repository.ScoreTypeRepository;
import com.example.smsbe.repository.SubjectRepository;
import com.example.smsbe.repository.TranscriptRepository;
import com.example.smsbe.request.AddScoreTypeRequest;
import com.example.smsbe.request.UpdateScoreTypeRequest;
import com.example.smsbe.request.UpdateSubjectRequest;
import com.example.smsbe.service.ConfigService;
import com.example.smsbe.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    private final ScoreTypeRepository scoreTypeRepository;
    private final SubjectRepository subjectRepository;
    private final ConfigRepository configRepository;

    public List<ScoreTypeDTO> getScoreTypes() {
        return scoreTypeRepository.findAllActive().stream()
                .map(scoreType -> MapperUtil.mapObject(scoreType, ScoreTypeDTO.class))
                .collect(Collectors.toList());
    }

    public List<ScoreTypeDTO> addScoreType(AddScoreTypeRequest req) {
        ScoreType scoreType = MapperUtil.mapObject(req, ScoreType.class);
        scoreTypeRepository.save(scoreType);
        return getScoreTypes();
    }

    public List<ScoreTypeDTO> updateScoreType(Integer id, UpdateScoreTypeRequest req) {
        ScoreType existing = scoreTypeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(404, "Score type not found"));
        existing.setType(req.getType());
        existing.setWeight(req.getWeight());
        scoreTypeRepository.save(existing);
        return getScoreTypes();
    }

    public List<ScoreTypeDTO> deleteScoreType(Integer id) {
        ScoreType scoreType = scoreTypeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(404, "Score type not found"));
        scoreType.setDeletedAt(new Date());
        scoreTypeRepository.save(scoreType);
        return getScoreTypes();
    }

    public List<SubjectDTO> getSubjects() {
        return subjectRepository.findAllActive().stream()
                .map(subject -> MapperUtil.mapObject(subject, SubjectDTO.class))
                .collect(Collectors.toList());
    }

    public List<SubjectDTO> addSubject(SubjectDTO subjectDTO) {
        Subject subject = MapperUtil.mapObject(subjectDTO, Subject.class);
        subjectRepository.save(subject);
        return getSubjects();
    }

    public List<SubjectDTO> updateSubject(String id, UpdateSubjectRequest req) {
        Subject existing = subjectRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(404, "Subject not found"));
        existing.setSubject(req.getSubject());
        existing.setEfficient(req.getEfficient());
        subjectRepository.save(existing);
        return getSubjects();
    }

    public List<SubjectDTO> deleteSubject(String id) {
        Subject subject = subjectRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(404, "Subject not found"));
        subject.setDeletedAt(new Date());
        subjectRepository.save(subject);
        return getSubjects();
    }
}
