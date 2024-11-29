package com.team1.caseStudy.team1.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportServiceTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ImportProperties importProperties;

    @InjectMocks
    private ImportService importService;

    @Test
    void testRun_Success() throws Exception {
        String testFilePath = "data/test-employees.csv";
        when(importProperties.getFilepath()).thenReturn(testFilePath);

        importService.run();

        verify(employeeService, times(1)).loadFromFile(testFilePath);
    }

    @Test
    void testRun_Exception() throws Exception {
        String testFilePath = "data/test-employees.csv";
        when(importProperties.getFilepath()).thenReturn(testFilePath);

        doThrow(new RuntimeException("File not found")).when(employeeService).loadFromFile(testFilePath);

        Exception exception = assertThrows(RuntimeException.class, () -> importService.run());
        Assertions.assertEquals("File not found", exception.getMessage());

        verify(employeeService, times(1)).loadFromFile(testFilePath);
    }
}
