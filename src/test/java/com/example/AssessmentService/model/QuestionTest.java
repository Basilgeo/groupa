package com.example.AssessmentService.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    @Test
    public void testQuestionModel() {
        Answer answer1 = new Answer();
        answer1.setOption("Option A");
        Answer answer2 = new Answer();
        answer2.setOption("Option B");

        List<Answer> answers = Arrays.asList(answer1, answer2);

        Question question = new Question();
        question.setQuestionText("Sample Question");
        question.setAnswers(answers);

        assertEquals("Sample Question", question.getQuestionText());
        assertEquals(2, question.getAnswers().size());
        assertEquals("Option A", question.getAnswers().get(0).getOption());
    }
}
