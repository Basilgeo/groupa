package com.example.AssessmentService.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetStatusTest {

    @Test
    public void testSetStatusEnum() {
        assertEquals("PENDING", SetStatus.PENDING.name());
        assertEquals("APPROVED", SetStatus.APPROVED.name());
    }
}
