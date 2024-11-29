package com.team1.caseStudy.team1.service;

import com.opencsv.CSVWriter;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExportService {

    private final EmployeeRepository employeeRepository;
    private final ExportProperties exportProperties;

    public String exportDataToCsv() throws IOException {
        String filePath = exportProperties.getFilepath();

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            String[] header = {"ID", "Name", "Email"};
            writer.writeNext(header);

            List<Employee> employees = employeeRepository.findAll();
            employees.forEach(employee -> {
                String[] data = {
                        String.valueOf(employee.getId()),
                        employee.getName(),
                        employee.getEmail()
                };
                writer.writeNext(data);
            });
        }
        return filePath;
    }
}


