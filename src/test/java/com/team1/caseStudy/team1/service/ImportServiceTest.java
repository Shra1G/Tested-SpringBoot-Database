package com.team1.caseStudy.team1.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ImportServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private ImportService importService;

    public ImportServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testRunLoadsDataSuccessfully() throws Exception {
        // Arrange
        String expectedFilePath = "src/main/resources/employees.csv";

        // Act
        importService.run();

        // Assert
        verify(employeeService).loadFromFile(expectedFilePath); // Ensure loadFromFile is called with the correct argument
        System.out.println("Data loaded from " + expectedFilePath); // Verify log output if required
    }
}
