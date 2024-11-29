package com.team1.caseStudy.team1.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ExportProperties exportProperties;

    @InjectMocks
    private ExportService exportService;

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = Arrays.asList(
                new Employee(1, "Mohit Nair", "Mohit.Nair@example.com"),
                new Employee(2, "Tom Cruise", "Tom.Cruise@example.com")
        );
    }

    @Test
    void testExportDataToCsv_Success() throws IOException, CsvException {
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);
        Mockito.when(exportProperties.getFilepath()).thenReturn("test-employees.csv");

        String filePath = exportService.exportDataToCsv();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            assertEquals(3, rows.size());
            assertArrayEquals(new String[]{"ID", "Name", "Email"}, rows.get(0));
            assertArrayEquals(new String[]{"1", "Mohit Nair", "Mohit.Nair@example.com"}, rows.get(1));
            assertArrayEquals(new String[]{"2", "Tom Cruise", "Tom.Cruise@example.com"}, rows.get(2));
        }

        new File(filePath).delete();
    }

    @Test
    void testExportDataToCsv_EmptyData() throws IOException {
        Mockito.when(employeeRepository.findAll()).thenReturn(List.of());
        Mockito.when(exportProperties.getFilepath()).thenReturn("test-empty.csv");

        String filePath = exportService.exportDataToCsv();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            assertEquals(1, rows.size());
            assertArrayEquals(new String[]{"ID", "Name", "Email"}, rows.get(0));
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        // Cleanup
        new File(filePath).delete();
    }

    @Test
    void testExportDataToCsv_FileWriteError() {
        Mockito.when(exportProperties.getFilepath()).thenReturn("/invalid-path/test.csv");

        IOException exception = assertThrows(IOException.class, () -> exportService.exportDataToCsv());
        assertFalse(exception.getMessage().contains("/invalid-path"));
    }
}
