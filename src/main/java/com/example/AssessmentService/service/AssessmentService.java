package com.example.AssessmentService.service;


import com.example.AssessmentService.dto.*;
import com.example.AssessmentService.exception.ResourceNotFoundException;
import com.example.AssessmentService.model.Answer;
import com.example.AssessmentService.model.Assessment;
import com.example.AssessmentService.model.Question;
import com.example.AssessmentService.model.SetStatus;
import com.example.AssessmentService.repo.AnswerRepository;
import com.example.AssessmentService.repo.AssessmentRepository;
import com.example.AssessmentService.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public Assessment createAssessment(AssessmentRequest assessmentRequest) {

        Assessment assessment = new Assessment();
        assessment.setSetName(assessmentRequest.getSetName());
        assessment.setDomain(assessmentRequest.getDomain());
        assessment.setCreatedBy(assessmentRequest.getCreatedBy());

        LocalDate date  = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String formattedDate = date.format(formatter);
        assessment.setCreatedDate(formattedDate);
        assessment.setStatus(SetStatus.PENDING);

        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : assessmentRequest.getQuestions()) {
            Question question = new Question();
            question.setQuestionText(questionRequest.getQuestion());
            question.setAssessment(assessment);
            question.setAnswers(new ArrayList<>()); // Initialize answers as empty list
            questions.add(question);
        }

        assessment.setQuestions(questions);
        Assessment savedAssessment = assessmentRepository.save(assessment);


        return savedAssessment;
    }

    public List<AssessmentDTO> getAllAssessments() {
        List<Assessment> assessments = assessmentRepository.findAll();
        return assessments.stream()
                .map(this::mapToAssessmentDTO)
                .collect(Collectors.toList());
    }

    public AssessmentResponse getAssessmentBySetName(String setName) {
        Assessment assessment = assessmentRepository.findBySetName(setName).orElse(null);
        if (assessment == null) {
            throw new ResourceNotFoundException("Assessment not found");
        }
        AssessmentResponse response = new AssessmentResponse();
        response.setAssessment(mapToAssessmentDTO(assessment));
        response.setQuestions(assessment.getQuestions());
        return response;
    }

    @Transactional
    public String updateQuestion(long setid, Long questionId, AnswerRequest answerReq ){
        Assessment assessment = assessmentRepository.findById(setid).orElse(null);
        if (assessment == null) {
            return "assessment not found";
        }

        Question questionToUpdate = assessment.getQuestions().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst().orElse(null);
        if(questionToUpdate==null) return "question not found";
        ;
        // Update question text if provided


        // Add answers if provided
        if (answerReq.getAnswers() != null) {
            List<Answer> answers = answerReq.getAnswers().stream()
                    .map(answerRequest -> {
                        Answer answer = new Answer();
                        answer.setOption(answerRequest.getOption());
                        answer.setSuggestion(answerRequest.getSuggestion());
                        answer.setQuestion(questionToUpdate);
                        return answer;
                    })
                    .collect(Collectors.toList());
            questionToUpdate.setAnswers(answers);
        }

        questionRepository.save(questionToUpdate);
        return "Question updated Successfully";
    }

//    @Transactional
//    public void deleteQuestion(String setName, Long questionId) {
//        Assessment assessment = assessmentRepository.findBySetName(setName);
//        if (assessment == null) {
//            throw new ResourceNotFoundException("Assessment not found");
//        }
//
//        Question questionToDelete = assessment.getQuestions().stream()
//                .filter(q -> q.getQuestionId().equals(questionId))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//
//        assessment.getQuestions().remove(questionToDelete);
//        questionRepository.deleteById(questionToDelete.getQuestionId());
//        assessmentRepository.save(assessment);
//    }

    @Transactional
    public Map<String, String> deleteQuestion(long setid, Long questionId) {
        Map<String, String> response = new HashMap<>();
        Assessment assessment = assessmentRepository.findById(setid).orElse(null);
        if (assessment == null) {
//            throw new ResourceNotFoundException("Assessment not found");
            response.put("message", "Assessment not found");
            return response;
        }

        Optional<Question> questionToDelete = assessment.getQuestions().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst();


        if (!questionToDelete.isPresent()) {
            response.put("message", "Question not found");
            return response;
        }

        assessment.getQuestions().remove(questionToDelete.get());
        questionRepository.deleteById(questionToDelete.get().getQuestionId());
        assessmentRepository.save(assessment);

        response.put("message", "Question deleted successfully");
        return response;
    }

    private AssessmentDTO mapToAssessmentDTO(Assessment assessment) {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setSetName(assessment.getSetName());
        assessmentDTO.setDomain(assessment.getDomain());
        assessmentDTO.setCreatedBy(assessment.getCreatedBy());
        // Set additional properties like created_date, modified_date, status if available
        return assessmentDTO;
    }

    public QuestionResponse getQuestionsSetName(String setName) {
        Assessment assessment = assessmentRepository.findBySetName(setName).orElse(null);
        if (assessment == null)
            return null;

        QuestionResponse response = new QuestionResponse();
        response.setQuestions(assessment.getQuestions());


        return response;
    }
}