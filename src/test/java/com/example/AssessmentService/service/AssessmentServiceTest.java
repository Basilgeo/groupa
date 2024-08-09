package com.example.AssessmentService.service;

import com.example.AssessmentService.dto.*;
import com.example.AssessmentService.exception.ResourceNotFoundException;
import com.example.AssessmentService.model.Answer;
import com.example.AssessmentService.model.Assessment;
import com.example.AssessmentService.model.Question;
import com.example.AssessmentService.repo.AnswerRepository;
import com.example.AssessmentService.repo.AssessmentRepository;
import com.example.AssessmentService.repo.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssessmentServiceTest {

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAssessment() {
        AssessmentRequest assessmentRequest = new AssessmentRequest();
        assessmentRequest.setSetName("Sample Set");
        assessmentRequest.setDomain("Sample Domain");
        assessmentRequest.setCreatedBy("User");

        List<QuestionRequest> questionRequests = new ArrayList<>();
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestion("Sample Question?");
        questionRequests.add(questionRequest);
        assessmentRequest.setQuestions(questionRequests);

        Assessment savedAssessment = new Assessment();
        savedAssessment.setSetName("Sample Set");
        when(assessmentRepository.save(any(Assessment.class))).thenReturn(savedAssessment);

        Assessment result = assessmentService.createAssessment(assessmentRequest);

        assertNotNull(result);
        assertEquals("Sample Set", result.getSetName());
        verify(assessmentRepository, times(1)).save(any(Assessment.class));
    }

    @Test
    void testGetAllAssessments() {
        List<Assessment> assessments = new ArrayList<>();
        Assessment assessment = new Assessment();
        assessment.setSetName("Sample Set");
        assessments.add(assessment);
        when(assessmentRepository.findAll()).thenReturn(assessments);

        List<AssessmentDTO> result = assessmentService.getAllAssessments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Set", result.get(0).getSetName());
        verify(assessmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAssessmentBySetName() {
        String setName = "Sample Set";
        Assessment assessment = new Assessment();
        assessment.setSetName(setName);
        when(assessmentRepository.findBySetName(setName)).thenReturn(Optional.of(assessment));

        AssessmentResponse result = assessmentService.getAssessmentBySetName(setName);

        assertNotNull(result);
        assertEquals(setName, result.getAssessment().getSetName());
        verify(assessmentRepository, times(1)).findBySetName(setName);
    }

    @Test
    void testUpdateQuestion() {
        long setId = 1L;
        long questionId = 1L;
        Assessment assessment = new Assessment();
        Question question = new Question();
        question.setQuestionId(questionId);
        assessment.setQuestions(List.of(question));
        when(assessmentRepository.findById(setId)).thenReturn(Optional.of(assessment));

        AnswerRequest answerRequest = new AnswerRequest();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answer.setOption("Option 1");
        answers.add(answer);
        answerRequest.setAnswers(answers);

        String result = assessmentService.updateQuestion(setId, questionId, answerRequest);

        assertEquals("Question updated Successfully", result);
        verify(questionRepository, times(1)).save(question);
    }

//    @Test
//    void testDeleteQuestion() {
//        // Arrange
//        Long questionId = 123L;
//        Long assessmentId = 1L;
//
//        Assessment assessment = new Assessment();
//        assessment.setSetid(assessmentId);
//        assessment.setSetName("Sample Set");
//        assessment.setDomain("Sample Domain");
//        assessment.setCreatedBy("User");
//
//        Question question = new Question();
//        question.setQuestionId(questionId);
//        question.setQuestionText("Sample Question");
//        question.setAssessment(assessment);
//
//        Answer answer1 = new Answer();
//        answer1.setAnswerId(1L);
//        answer1.setOption("Option 1");
//        answer1.setSuggestion("Suggestion 1");
//        answer1.setQuestion(question);
//
//        Answer answer2 = new Answer();
//        answer2.setAnswerId(2L);
//        answer2.setOption("Option 2");
//        answer2.setSuggestion("Suggestion 2");
//        answer2.setQuestion(question);
//
//        question.setAnswers(List.of(answer1, answer2));
//        assessment.setQuestions(List.of(question));
//
//        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));
//        when(questionRepository.existsById(questionId)).thenReturn(true);
//
//        // Act
//        Map<String, String> response = assessmentService.deleteQuestion(assessmentId, questionId);
//
//        // Assert
//        assertEquals("Question deleted successfully", response.get("message"));
//        verify(questionRepository).deleteById(questionId);
//        assertFalse(assessment.getQuestions().contains(question));
//    }
@Test
void testCreateAssessment_DuplicateEntry() {
    // Arrange
    AssessmentRequest assessmentRequest = new AssessmentRequest();
    assessmentRequest.setSetName("Duplicate Set");
    assessmentRequest.setDomain("Sample Domain");
    assessmentRequest.setCreatedBy("User");
    assessmentRequest.setQuestions(new ArrayList<>());

    // Mock the repository to throw a DataIntegrityViolationException on save
    when(assessmentRepository.save(any(Assessment.class)))
            .thenThrow(new DataIntegrityViolationException("Duplicate entry found"));

    // Act & Assert
    DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
        assessmentService.createAssessment(assessmentRequest);
    });
    assertEquals("Duplicate entry found", exception.getMessage());
}


    @Test
    void testGetQuestionsBySetName_SetNotFound() {
        // Arrange
        String setName = "NonExistentSet";
        when(assessmentService.getQuestionsSetName(setName)).thenThrow(new ResourceNotFoundException("Set not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            assessmentService.getQuestionsSetName(setName);
        });
        assertEquals("Set not found", exception.getMessage());
    }



    @Test
    void testUpdateQuestion_Success() {
        // Arrange
        long setId = 1L;
        long questionId = 123L;

        Assessment assessment = new Assessment();
        assessment.setSetid(setId);
        assessment.setSetName("Sample Set");
        assessment.setDomain("Sample Domain");
        assessment.setCreatedBy("User");

        Question existingQuestion = new Question();
        existingQuestion.setQuestionId(questionId);
        existingQuestion.setQuestionText("Old Question Text");
        existingQuestion.setAssessment(assessment);

        AnswerRequest answerRequest = new AnswerRequest();
        Answer answer = new Answer();
        answer.setOption("Option 1");
        answer.setSuggestion("Suggestion 1");
        answerRequest.setAnswers(List.of(answer));

        assessment.setQuestions(List.of(existingQuestion));

        when(assessmentRepository.findById(setId)).thenReturn(Optional.of(assessment));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        String result = assessmentService.updateQuestion(setId, questionId, answerRequest);

        // Assert
        assertEquals("Question updated Successfully", result);
        assertNotNull(existingQuestion.getAnswers());
        assertEquals(1, existingQuestion.getAnswers().size());
        assertEquals("Option 1", existingQuestion.getAnswers().get(0).getOption());
        assertEquals("Suggestion 1", existingQuestion.getAnswers().get(0).getSuggestion());
        verify(questionRepository).save(existingQuestion);
    }













}
