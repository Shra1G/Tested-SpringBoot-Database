package com.team1.caseStudy.team1.service;

import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("Mohit Nair");
        employee.setEmail("Mohit.Nair@example.com");
    }

    @Test
    void testSaveEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals(employee.getId(), savedEmployee.getId());
        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.getId(), foundEmployee.get().getId());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1);

        assertFalse(foundEmployee.isPresent());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testGetEmployeeByEmail_Success() {
        when(employeeRepository.findByEmail("Mohit.Nair@example.com")).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeByEmail("Mohit.Nair@example.com");

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.getEmail(), foundEmployee.get().getEmail());
        verify(employeeRepository, times(1)).findByEmail("Mohit.Nair@example.com");
    }

    @Test
    void testGetEmployeeByEmail_NotFound() {
        when(employeeRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = employeeService.getEmployeeByEmail("unknown@example.com");

        assertFalse(foundEmployee.isPresent());
        verify(employeeRepository, times(1)).findByEmail("unknown@example.com");
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = List.of(employee, new Employee(2, "Tom Cruise", "Tom.Cruise@example.com"));
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> allEmployees = employeeService.getAllEmployees();

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
