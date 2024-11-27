package com.team1.caseStudy.team1.service;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;  // Mocking the EmployeeRepository

    @InjectMocks
    private ExportService exportService;    // Injecting the mocked repository into the service

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExportDataToCsv() throws IOException {
        // Arrange: Mock the Employee list to return from the repository
        Employee employee1 = new Employee(1, "John", "john.doe@gmail.com");
        Employee employee2 = new Employee(2, "Sachin", "Sachin.singh@gmail.com");
        List<Employee> employees = Arrays.asList(employee1, employee2);

        // Mock the behavior of employeeRepository.findAll() to return the mock employee list
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act: Call the export method
        String filePath = exportService.exportDataToCsv();

        // Assert: Verify if the file was created successfully
        File file = new File(filePath);
        assertTrue(file.exists(), "CSV file should be created");

        // You can also check the file content in a more advanced test, but for now, we check if the file exists
        // Clean up: Delete the file after the test to keep things tidy
    }


    @Test
    void testExportDataToCsv_noEmployees() throws IOException {
        // Arrange: Mock the employee list to be empty
        when(employeeRepository.findAll()).thenReturn(List.of());

        // Act: Call the export method
        String filePath = exportService.exportDataToCsv();

        // Assert: Verify that the file is created even when there are no employees
        File file = new File(filePath);
        assertTrue(file.exists(), "CSV file should be created even when there are no employees");

    }

    @Test
    public void testExportDataToCsv_ErrorWritingFile() {
        // Simulate an IOException
        when(employeeRepository.findAll()).thenThrow(new RuntimeException("Error writing CSV file"));

        // Call the method and assert that it throws an exception
        try {
            exportService.exportDataToCsv();
        } catch (RuntimeException | IOException e) {
            assert e.getMessage().equals("Error writing CSV file");
        }
    }
}