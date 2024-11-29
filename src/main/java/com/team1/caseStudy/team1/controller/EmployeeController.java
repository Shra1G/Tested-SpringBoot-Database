package com.team1.caseStudy.team1.controller;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.service.ExportService;
import com.team1.caseStudy.team1.service.ImportService;
import com.team1.caseStudy.team1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ExportService exportService;
    private final ImportService importService;

    @PostMapping()
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        try {
            return employeeService.getEmployeeById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        try {
            return employeeService.getEmployeeById(id)
                    .map(employee -> {
                        employee.setName(updatedEmployee.getName());
                        employee.setEmail(updatedEmployee.getEmail());
                        return ResponseEntity.ok(employeeService.saveEmployee(employee));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        try {
            return employeeService.getEmployeeByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}