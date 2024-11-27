package com.team1.caseStudy.team1.service;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Employee
        employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setEmail("john.doe@gmail.com");
    }

    @Test
    void testSaveEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("John Doe", savedEmployee.getName());
        assertEquals("john.doe@gmail.com", savedEmployee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1);

        assertTrue(foundEmployee.isPresent());
        assertEquals("John Doe", foundEmployee.get().getName());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testGetEmployeeByEmail() {
        when(employeeRepository.findByEmail("john.doe@gmail.com")).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeByEmail("john.doe@gmail.com");

        assertTrue(foundEmployee.isPresent());
        assertEquals("John Doe", foundEmployee.get().getName());
        verify(employeeRepository, times(1)).findByEmail("john.doe@gmail.com");
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee, new Employee(2, "Sachin Doe", "Sachin.doe@gmail.com"));
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertNotNull(allEmployees);
        assertEquals(2, allEmployees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1);

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).deleteById(1);
    }
}
