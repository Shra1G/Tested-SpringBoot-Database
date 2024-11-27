package com.team1.caseStudy.team1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ImportService implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        String filePath = "src/main/resources/employees.csv";
        employeeService.loadFromFile(filePath);
        System.out.println("Data loaded from " + filePath);
    }
}