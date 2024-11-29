package com.team1.caseStudy.team1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ExportPropertiesTest {

    @InjectMocks
    private ExportProperties exportProperties;

    @Test
    void testGetAndSetFilename() {
        assertNull(exportProperties.getFilepath(), "Default filename is null");

        String testFilename = "export/test-file.csv";
        exportProperties.setFilepath(testFilename);

        assertEquals(testFilename, exportProperties.getFilepath(), "Filename should match the value set");
    }
}
