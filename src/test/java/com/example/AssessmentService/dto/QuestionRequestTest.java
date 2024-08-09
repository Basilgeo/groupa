package com.example.AssessmentService.dto;

import com.example.AssessmentService.model.Answer;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionRequestTest {

    @Test
    public void testQuestionRequest() {
        Answer answer1 = new Answer();
        answer1.setOption("Option A");
        Answer answer2 = new Answer();
        answer2.setOption("Option B");

        List<Answer> answers = Arrays.asList(answer1, answer2);

        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestion("Sample Question");
        questionRequest.setAnswers(answers);

        assertEquals("Sample Question", questionRequest.getQuestion());
        assertEquals(2, questionRequest.getAnswers().size());
        assertEquals("Option A", questionRequest.getAnswers().get(0).getOption());
    }
}
