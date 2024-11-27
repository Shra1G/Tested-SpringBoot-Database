package com.team1.caseStudy.team1.controller;


import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.service.ExportService;
import com.team1.caseStudy.team1.service.ImportService;
import com.team1.caseStudy.team1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ExportService exportService;
    @Autowired
    private ImportService importService;

    @PostMapping()
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setEmail(updatedEmployee.getEmail());
                    return ResponseEntity.ok(employeeService.saveEmployee(employee));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() throws IOException {
        exportService.exportDataToCsv();
        return ResponseEntity.ok("CSV exported successfully!");
    }

    @GetMapping("/import")
    public ResponseEntity<String> importToCSV() throws Exception {
        importService.run();
        return ResponseEntity.ok("CSV import successfully!");
    }

    @GetMapping("/email/{email}")
    public Optional<Employee> getEmployeeByEmail(@PathVariable String email) throws Exception {
        return employeeService.getEmployeeByEmail(email);
    }
}