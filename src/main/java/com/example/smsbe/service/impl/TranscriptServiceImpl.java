package com.example.smsbe.service.impl;

import com.example.smsbe.dto.*;
import com.example.smsbe.entity.*;
import com.example.smsbe.entity.Class;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.*;
import com.example.smsbe.request.AddScoreRequest;
import com.example.smsbe.request.UpdateScoreRequest;
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
    private final StudentRepository studentRepository;
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

    public ScoreDTO addScore(AddScoreRequest req) {
        System.out.println(req.getClassDetailId());
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

        transcript = transcriptRepository.save(transcript);
        return new ScoreDTO()
                .setId(transcript.getId())
                .setScore(transcript.getScore());
    }

    public void deleteScore(Integer transcriptId) {
        Transcript transcript = transcriptRepository.findById(transcriptId).orElseThrow(() ->
                new AppException(404, "Transcript not found")
        );

        transcriptRepository.delete(transcript);
    }

    public void updateScore(Integer transcriptId, UpdateScoreRequest req) {
        Transcript transcript = transcriptRepository.findById(transcriptId).orElseThrow(() ->
                new AppException(404, "Transcript not found")
        );
        transcript.setScore(req.getScore());
        transcriptRepository.save(transcript);

    }

    public List<TranscriptDTO> getTranscriptByClassTermAndSubject(Integer classId, Integer term, String subjectId) {
    List<ClassDetail> students = classDetailRepository.findByClassTerm(classId, getTerm(term));
    List<Transcript> transcripts = transcriptRepository.findByClassTermAndSubject(classId, getTerm(term), subjectId);

    Map<Integer, List<Transcript>> groupedByStudent = transcripts.stream()
            .collect(Collectors.groupingBy(transcript -> transcript.getClassDetail().getId()));

    List<TranscriptDTO> transcriptDTOs = new ArrayList<>();

    for (ClassDetail student : students) {
        List<Transcript> studentTranscripts = groupedByStudent.getOrDefault(student.getId(), Collections.emptyList());
        Map<ScoreTypeDTO, List<ScoreDTO>> scores = studentTranscripts.stream()
                .collect(Collectors.groupingBy(transcript -> MapperUtil.mapObject(transcript.getScoreType(), ScoreTypeDTO.class),
                        Collectors.mapping(transcript -> new ScoreDTO()
                                .setScore(transcript.getScore())
                                .setId(transcript.getId()), Collectors.toList())));
        Double averageScore = scores.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getWeight() * entry.getValue().stream().mapToDouble(ScoreDTO::getScore).sum())
                .sum() / (scores.keySet().isEmpty() ? 1 : scores.keySet().stream().mapToDouble(ScoreTypeDTO::getWeight).sum());
        Map<Integer, List<ScoreDTO>> scoresMap = new HashMap<>();
        for (Map.Entry<ScoreTypeDTO, List<ScoreDTO>> entry : scores.entrySet()) {
            List<ScoreDTO> scoreList = scoresMap.getOrDefault(entry.getKey().getId(), new ArrayList<>());
            scoreList.addAll(entry.getValue());
            scoresMap.put(entry.getKey().getId(), scoreList);
        }
        transcriptDTOs.add(new TranscriptDTO()
                        .setClassDetail(MapperUtil.mapObject(student, ClassDetailDTO.class))
                        .setScores(scoresMap)
                        .setAvgScore(averageScore));
    }

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
            Map<Integer, Double> gpas = new HashMap<>();
            subjects.forEach(subject -> {
                List<TranscriptDTO> transcripts = getTranscriptByClassTermAndSubject(aClass.getId(), term, subject.getId());
                transcripts.forEach(transcript -> {
                    double avgScore = transcript.getAvgScore() * subject.getEfficient();
                    if (gpas.containsKey(transcript.getClassDetail().getId())) {
                        avgScore += gpas.get(transcript.getClassDetail().getId());
                    }
                    gpas.put(transcript.getClassDetail().getId(), avgScore);
                });
            });
            Double totalEfficient = subjects.stream().mapToDouble(Subject::getEfficient).sum();
            gpas.forEach((key, value) -> {
                double gpa = value / totalEfficient;
                gpas.put(key, gpa);
            });
            int totalStudents = aClass.getTotal();
            int passedStudents = (int) gpas.entrySet().stream()
                    .filter(gpa -> gpa.getValue() >= minScorePass)
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

