package com.example.AssessmentService.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentDTOTest {

    @Test
    public void testAssessmentDTO() {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setSetName("Test Set");
        assessmentDTO.setDomain("Testing");
        assessmentDTO.setCreatedBy("Tester");

        assertEquals("Test Set", assessmentDTO.getSetName());
        assertEquals("Testing", assessmentDTO.getDomain());
        assertEquals("Tester", assessmentDTO.getCreatedBy());
    }
}
