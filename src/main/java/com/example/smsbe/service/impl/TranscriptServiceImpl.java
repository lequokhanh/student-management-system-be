package com.example.smsbe.service.impl;

import com.example.smsbe.dto.ClassTermDTO;
import com.example.smsbe.dto.ScoreTypeDTO;
import com.example.smsbe.dto.StudentDTO;
import com.example.smsbe.dto.TranscriptDTO;
import com.example.smsbe.entity.*;
import com.example.smsbe.entity.Class;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.*;
import com.example.smsbe.request.AddScoreRequest;
import com.example.smsbe.response.TranscriptSummaryResponse;
import com.example.smsbe.service.TranscriptService;
import com.example.smsbe.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranscriptServiceImpl implements TranscriptService {

    private final TranscriptRepository transcriptRepository;
    private final ConfigRepository configRepository;
    private final ClassRepository classRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final ClassTermRepository classTermRepository;
    private final SubjectRepository subjectRepository;
    private final ClassDetailRepository classDetailRepository;
    private final ScoreTypeRepository scoreTypeRepository;
    private static final String MIN_SCORE_PASS = "minScorePass";

    private ClassTerm.Term getTerm(Integer term) {
        return switch (term) {
            case 1 -> ClassTerm.Term._1;
            case 2 -> ClassTerm.Term._2;
            default -> throw new AppException(400, "Invalid term");
        };
    }

    private int getMinScorePass() {
        Optional<GlobalConfig> config = configRepository.findByConfigKey(MIN_SCORE_PASS);
        if (config.isEmpty() || config.get().getDeletedAt() != null) {
            throw new AppException(500, "Configuration for minScorePass is missing");
        }
        return Integer.parseInt(config.get().getValue());
    }

    public void addScore(AddScoreRequest req) {
        ClassDetail classDetail = classDetailRepository.findById(req.getClassDetailId()).orElseThrow(() ->
                new AppException(404, "Class detail not found")
        );

        Subject subject = subjectRepository.findById(req.getSubjectId()).orElseThrow(() ->
                new AppException(404, "Subject not found")
        );

        ScoreType scoreType = scoreTypeRepository.findById(req.getTypeId()).orElseThrow(() ->
                new AppException(404, "Score type not found")
        );

        Transcript transcript = new Transcript()
                .setClassDetail(classDetail)
                .setSubject(subject)
                .setScoreType(scoreType)
                .setScore(req.getScore());

        transcriptRepository.save(transcript);
    }

    public List<TranscriptDTO> getTranscriptByClassTermAndSubject(Integer classId, Integer term, String subjectId) {
        List<Transcript> transcripts = transcriptRepository.findByClassTermAndSubject(classId, getTerm(term), subjectId);

        Map<Integer, List<Transcript>> groupedByStudent = transcripts.stream()
                .collect(Collectors.groupingBy(transcript -> transcript.getClassDetail().getId()));

        List<TranscriptDTO> transcriptDTOs = new ArrayList<>();

        groupedByStudent.forEach((studentId, studentTranscripts) -> {
            Map<ScoreTypeDTO, List<Double>> scores = studentTranscripts.stream()
                    .collect(Collectors.groupingBy(transcript -> MapperUtil.mapObject(transcript.getScoreType(), ScoreTypeDTO.class),
                            Collectors.mapping(Transcript::getScore, Collectors.toList())));
            // avgScore calculated by sum product of score and weight divided by sum of weight
            Double averageScore = scores.entrySet().stream()
                    .mapToDouble(entry -> entry.getKey().getWeight() * entry.getValue().stream().mapToDouble(Double::doubleValue).sum())
                    .sum() / scores.entrySet().stream().mapToDouble(entry -> entry.getKey().getWeight()).sum();
            transcriptDTOs.add(MapperUtil.mapObject(studentTranscripts.get(0), TranscriptDTO.class)
                    .setScores(scores)
                    .setAvgScore(averageScore));
        });

        return transcriptDTOs;
    }


    public List<TranscriptSummaryResponse> getTranscriptSummaryByClassTermAndSubject(Integer schoolYearID, Integer term, String subjectId) {
        // Get the minimum score to pass
        int minScorePass = getMinScorePass();

        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearID).orElseThrow(() ->
                new AppException(404, "School year not found")
        );

        List<Class> classes = classRepository.findBySchoolYear(schoolYear);

        List<TranscriptSummaryResponse> summaryResponses = new ArrayList<>();

        for (Class aClass : classes) {
            // Fetch transcripts for the specific subject in the class term
            ClassTerm classTerm = classTermRepository.findByAClassIdAndTerm(aClass.getId(), getTerm(term)).orElseThrow(() ->
                    new AppException(404, "Class term not found")
            );
            List<TranscriptDTO> transcripts = getTranscriptByClassTermAndSubject(aClass.getId(), term, subjectId);
            int totalStudents = aClass.getTotal();
            int passedStudents = (int) transcripts.stream()
                    .filter(transcript -> transcript.getAvgScore() >= minScorePass)
                    .count();

            double passRate = totalStudents > 0 ? (double) passedStudents / totalStudents * 100 : 0;

            summaryResponses.add(new TranscriptSummaryResponse()
                    .setClassTerm(MapperUtil.mapObject(classTerm, ClassTermDTO.class))
                    .setTotal(totalStudents)
                    .setPassed(passedStudents)
                    .setRate(passRate));
        }

        return summaryResponses;
    }



    public List<TranscriptSummaryResponse> getTranscriptSummaryByClassTerm(Integer schoolYearID, Integer term) {
        int minScorePass = getMinScorePass();

        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearID).orElseThrow(() ->
                new AppException(404, "School year not found")
        );

        List<Class> classes = classRepository.findBySchoolYear(schoolYear);

        List<TranscriptSummaryResponse> summaryResponses = new ArrayList<>();

        List<Subject> subjects = subjectRepository.findAllActive();

        for (Class aClass : classes) {
            ClassTerm classTerm = classTermRepository.findByAClassIdAndTerm(aClass.getId(), getTerm(term)).orElseThrow(() ->
                    new AppException(404, "Class term not found")
            );
            // calculate gpa for each studen in the class, if the student has no transcript, the gpa is 0, if the student has transcript, the gpa is calculated by sum product of avg score of each subject and efficient of subject divided by sum of efficient of subject
            List<TranscriptDTO> transcripts = getTranscriptByClassTermAndSubject(aClass.getId(), term, null);
            List<Double> gpas = transcripts.stream()
                    .map(transcript -> {
                        double gpa = subjects.stream()
                                .mapToDouble(subject -> {
                                    List<Transcript> subjectTranscripts = transcriptRepository.findByClassTermAndSubject(aClass.getId(), getTerm(term), subject.getId());
                                    if (subjectTranscripts.isEmpty()) {
                                        return 0;
                                    }
                                    double avgScore = subjectTranscripts.stream()
                                            .mapToDouble(Transcript::getScore)
                                            .average()
                                            .orElse(0);
                                    return avgScore * subject.getEfficient();
                                })
                                .sum();
                        double totalEfficient = subjects.stream()
                                .mapToDouble(Subject::getEfficient)
                                .sum();
                        return totalEfficient > 0 ? gpa / totalEfficient : 0;
                    })
                    .toList();
            int totalStudents = aClass.getTotal();
            int passedStudents = (int) gpas.stream()
                    .filter(gpa -> gpa >= minScorePass)
                    .count();
            double passRate = totalStudents > 0 ? (double) passedStudents / totalStudents * 100 : 0;
            summaryResponses.add(new TranscriptSummaryResponse()
                    .setClassTerm(MapperUtil.mapObject(classTerm, ClassTermDTO.class))
                    .setTotal(totalStudents)
                    .setPassed(passedStudents)
                    .setRate(passRate));
        }
        return summaryResponses;
    }
}

