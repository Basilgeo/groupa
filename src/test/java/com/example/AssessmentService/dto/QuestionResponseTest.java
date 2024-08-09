package com.example.AssessmentService.dto;

import com.example.AssessmentService.model.Question;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionResponseTest {

    @Test
    public void testQuestionResponse() {
        Question question1 = new Question();
        question1.setQuestionText("Question 1");
        Question question2 = new Question();
        question2.setQuestionText("Question 2");

        List<Question> questions = Arrays.asList(question1, question2);

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(questions);

        assertEquals(2, questionResponse.getQuestions().size());
        assertEquals("Question 1", questionResponse.getQuestions().get(0).getQuestionText());
    }
}
