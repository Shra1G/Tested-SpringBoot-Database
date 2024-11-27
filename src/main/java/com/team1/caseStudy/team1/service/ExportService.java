package com.team1.caseStudy.team1.service;

import com.opencsv.CSVWriter;
import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Setter
public class ExportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String exportDataToCsv() throws IOException {
            // Use ClassPathResource to locate the "resources" folder
            File resourceDirectory = new ClassPathResource("employees1.csv").getFile();
            String filePath = resourceDirectory.getAbsolutePath();

            // Write to the CSV file
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
                String[] header = {"ID", "Name", "Email"};
                writer.writeNext(header);

                List<Employee> employees = employeeRepository.findAll();
                for (Employee employee : employees) {
                    String[] data = {
                            String.valueOf(employee.getId()),
                            employee.getName(),
                            employee.getEmail()
                    };
                    writer.writeNext(data);
                }
            }
            return filePath;
    }
}