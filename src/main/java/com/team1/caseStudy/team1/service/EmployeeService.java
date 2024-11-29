package com.team1.caseStudy.team1.service;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public void loadFromFile(String filePath) throws IOException {
        BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
            br.lines().skip(1).forEach(line -> {
                String[] data = line.split(",");
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(data[0].trim()));
                employee.setName(data[1].trim());
                employee.setEmail(data[2].trim());
                employeeRepository.save(employee);
            });
        }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}