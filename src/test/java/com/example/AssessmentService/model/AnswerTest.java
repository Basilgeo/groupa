package com.example.AssessmentService.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerTest {

    @Test
    public void testAnswerModel() {
        Answer answer = new Answer();
        answer.setOption("Option A");

        assertEquals("Option A", answer.getOption());
    }
}
