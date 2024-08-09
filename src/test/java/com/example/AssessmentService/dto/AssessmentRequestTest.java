//package com.example.AssessmentService.dto;
//
//import com.example.AssessmentService.model.Question;
//import org.junit.jupiter.api.Test;
//import java.util.Arrays;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AssessmentRequestTest {
//
//    @Test
//    public void testAssessmentRequest() {
//        Question question1 = new Question();
//        question1.setQuestionText("Question 1");
//        Question question2 = new Question();
//        question2.setQuestionText("Question 2");
//
//        List<QuestionRequest> questions = Arrays.asList(
//                new QuestionRequest("Set 1", "Domain", "Creator", Arrays.asList(question1)),
//                new QuestionRequest("Set 2", "Domain", "Creator", Arrays.asList(question2))
//        );
//
//        AssessmentRequest assessmentRequest = new AssessmentRequest();
//        assessmentRequest.setSetName("Test Set");
//        assessmentRequest.setDomain("Testing");
//        assessmentRequest.setCreatedBy("Tester");
//        assessmentRequest.setQuestions(questions);
//
//        assertEquals("Test Set", assessmentRequest.getSetName());
//        assertEquals(2, assessmentRequest.getQuestions().size());
//        assertEquals("Question 1", assessmentRequest.getQuestions().get(0).getQuestionText());
//    }
//}
