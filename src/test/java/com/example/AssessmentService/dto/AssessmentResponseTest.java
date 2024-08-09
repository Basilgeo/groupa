package com.example.AssessmentService.dto;

//import com.example.AssessmentService.model.AssessmentDTO;
import com.example.AssessmentService.model.Question;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentResponseTest {

    @Test
    public void testAssessmentResponse() {
        Question question1 = new Question();
        question1.setQuestionText("Question 1");
        Question question2 = new Question();
        question2.setQuestionText("Question 2");

        List<Question> questions = Arrays.asList(question1, question2);

        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setSetName("Test Set");
        assessmentDTO.setDomain("Testing");
        assessmentDTO.setCreatedBy("Tester");

        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessment(assessmentDTO);
        assessmentResponse.setQuestions(questions);

        assertEquals("Test Set", assessmentResponse.getAssessment().getSetName());
        assertEquals(2, assessmentResponse.getQuestions().size());
        assertEquals("Question 1", assessmentResponse.getQuestions().get(0).getQuestionText());
    }
}
