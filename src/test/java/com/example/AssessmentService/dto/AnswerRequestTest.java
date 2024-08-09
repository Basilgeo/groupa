package com.example.AssessmentService.dto;

import com.example.AssessmentService.model.Answer;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerRequestTest {

    @Test
    public void testAnswerRequest() {
        Answer answer1 = new Answer();
        answer1.setOption("Option A");
        Answer answer2 = new Answer();
        answer2.setOption("Option B");

        List<Answer> answers = Arrays.asList(answer1, answer2);

        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setAnswers(answers);

        assertEquals(2, answerRequest.getAnswers().size());
        assertEquals("Option A", answerRequest.getAnswers().get(0).getOption());
    }
}
