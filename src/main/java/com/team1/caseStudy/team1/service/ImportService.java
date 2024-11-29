package com.team1.caseStudy.team1.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
@ConfigurationProperties(prefix = "import")
public class ImportService implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final ImportProperties importProperties;

    @Override
    public void run(String... args) throws Exception {
        employeeService.loadFromFile(importProperties.getFilepath());
        log.info("Data loaded from {}", importProperties.getFilepath());
    }
}