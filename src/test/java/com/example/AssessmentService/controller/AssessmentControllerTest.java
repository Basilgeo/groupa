package com.example.AssessmentService.controller;

import com.example.AssessmentService.dto.*;
import com.example.AssessmentService.exception.ResourceNotFoundException;
import com.example.AssessmentService.model.Answer;
import com.example.AssessmentService.model.Assessment;
import com.example.AssessmentService.model.Question;
import com.example.AssessmentService.service.AssessmentService;
import org.aspectj.lang.annotation.Before;
//import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AssessmentControllerTest {

    @Mock
    private AssessmentService assessmentService;

    @InjectMocks
    private AssessmentController assessmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAssessments() {
        // Mock data
        List<AssessmentDTO> assessmentDTOList = new ArrayList<>();
        assessmentDTOList.add(new AssessmentDTO());
        assessmentDTOList.add(new AssessmentDTO());

        // Mock service method
        when(assessmentService.getAllAssessments()).thenReturn(assessmentDTOList);

        // Call controller method
        ResponseEntity<List<AssessmentDTO>> responseEntity = assessmentController.getAllAssessments();

        // Assert response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(assessmentDTOList, responseEntity.getBody());

        // Verify service method invocation
        verify(assessmentService, times(1)).getAllAssessments();
    }

    @Test
    public void testCreateAssessment_Success() {
        // Mock data
        AssessmentRequest assessmentRequest = new AssessmentRequest();
        assessmentRequest.setSetName("TestSet");
        assessmentRequest.setDomain("TestDomain");
        assessmentRequest.setCreatedBy("TestUser");
        List<QuestionRequest> questions = new ArrayList<>();
        questions.add(new QuestionRequest());
        assessmentRequest.setQuestions(questions);

        Assessment createdAssessment = new Assessment();
        createdAssessment.setSetid(1L);
        createdAssessment.setSetName("TestSet");
        createdAssessment.setDomain("TestDomain");
        createdAssessment.setCreatedBy("TestUser");

        // Mock service method
        when(assessmentService.createAssessment(any(AssessmentRequest.class))).thenReturn(createdAssessment);

        // Call controller method
        ResponseEntity<?> responseEntity = assessmentController.createAssessment(assessmentRequest);

        // Assert response status and body
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdAssessment, responseEntity.getBody());

        // Verify service method invocation
        verify(assessmentService, times(1)).createAssessment(any(AssessmentRequest.class));
    }

    @Test
    public void testCreateAssessment_DuplicateEntry() {
        // Mock data
        AssessmentRequest assessmentRequest = new AssessmentRequest();
        assessmentRequest.setSetName("TestSet");
        assessmentRequest.setDomain("TestDomain");
        assessmentRequest.setCreatedBy("TestUser");
        List<QuestionRequest> questions = new ArrayList<>();
        questions.add(new QuestionRequest());
        assessmentRequest.setQuestions(questions);

        // Mock service method to throw DataIntegrityViolationException (simulating duplicate entry)
        when(assessmentService.createAssessment(any(AssessmentRequest.class)))
                .thenThrow(new RuntimeException("Duplicate entry found"));

        // Call controller method
        ResponseEntity<?> responseEntity = assessmentController.createAssessment(assessmentRequest);

        // Assert response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Duplicate set entry found", responseEntity.getBody());

        // Verify service method invocation
        verify(assessmentService, times(1)).createAssessment(any(AssessmentRequest.class));
    }

    @Test
    public void testGetQuestionsBySetName_Success() {
        // Mock data
        String setName = "TestSet";
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(new ArrayList<>());

        // Mock service method
        when(assessmentService.getQuestionsSetName(setName)).thenReturn(questionResponse);

        // Call controller method
        ResponseEntity<?> responseEntity = assessmentController.getQuestionsBySetName(setName);

        // Assert response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questionResponse, responseEntity.getBody());

        // Verify service method invocation
        verify(assessmentService, times(1)).getQuestionsSetName(setName);
    }

    @Test
    public void testGetQuestionsBySetName_SetNotFound() {
        // Mock data
        String setName = "NonExistentSet";

        // Mock service method to throw ResourceNotFoundException (simulating set not found)
        when(assessmentService.getQuestionsSetName(setName)).thenThrow(new ResourceNotFoundException("Set not found"));

        // Call controller method
        ResponseEntity<?> responseEntity = assessmentController.getQuestionsBySetName(setName);

        // Assert response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Set not found", responseEntity.getBody());

        // Verify service method invocation
        verify(assessmentService, times(1)).getQuestionsSetName(setName);
    }

    @Test
    public void testUpdateQuestion_Success() {
        // Mock data
        long setid = 1L;
        Long questionId = 1L;
        AnswerRequest answerRequest = new AnswerRequest();
        Answer answer1 = new Answer();
        answer1.setOption("A");
        answer1.setSuggestion("Suggestion A");

        Answer answer2 = new Answer();
        answer2.setOption("B");
        answer2.setSuggestion("Suggestion B");

        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);

        answerRequest.setAnswers(answers);

        // Mock service method
        doNothing().when(assessmentService).updateQuestion(setid, questionId, answerRequest);

        // Call controller method
        ResponseEntity<String> responseEntity = assessmentController.updateQuestion(setid, questionId, answerRequest);

        // Assert response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify service method invocation
        verify(assessmentService, times(1)).updateQuestion(setid, questionId, answerRequest);
    }
//    @Test
//    public void testDeleteQuestion_Success() {
//        // Mock data
//        long setid = 1L;
//        Long questionId = 1L;
//        Map<String, String> deleteResponse = new HashMap<>();
//        deleteResponse.put("message", "Question deleted successfully");
//
//        // Mock service method
//        when(assessmentService.deleteQuestion(anyString(), anyLong())).thenReturn(deleteResponse);
//
//        // Call controller method
//        ResponseEntity<Map<String, String>> responseEntity = assessmentController.deleteQuestion(String.valueOf(setid), questionId);
//
//        // Assert response status and body
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(deleteResponse, responseEntity.getBody());
//
//        // Verify service method invocation
//        verify(assessmentService, times(1)).deleteQuestion(String.valueOf(setid), questionId);
//    }
}
