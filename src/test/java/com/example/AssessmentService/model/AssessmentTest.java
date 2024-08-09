package com.example.AssessmentService.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentTest {

    @Test
    public void testAssessmentModel() {
        Question question1 = new Question();
        question1.setQuestionText("Question 1");
        Question question2 = new Question();
        question2.setQuestionText("Question 2");

        List<Question> questions = Arrays.asList(question1, question2);

        Assessment assessment = new Assessment();
        assessment.setSetName("Test Set");
        assessment.setDomain("Testing");
        assessment.setCreatedBy("Tester");
        assessment.setQuestions(questions);

        assertEquals("Test Set", assessment.getSetName());
        assertEquals(2, assessment.getQuestions().size());
        assertEquals("Question 1", assessment.getQuestions().get(0).getQuestionText());
    }
}
