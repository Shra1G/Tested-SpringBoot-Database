package com.team1.caseStudy.team1.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.team1.caseStudy.team1.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveEmployee() {
        // Arrange
        Employee employee = new Employee(1,"Aryan", "Aryan@yahoo.com");

        // Act
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert
        assertEquals("Aryan", savedEmployee.getName());
        assertEquals("Aryan@yahoo.com", savedEmployee.getEmail());
    }

    @Test
    void testFindByEmail_WhenEmailExists() {
        // Arrange
        Employee employee = new Employee(1,"Lionel Ronaldo", "Lionel.Ronaldo@yahoo.com");
        employeeRepository.save(employee);

        Optional<Employee> result = employeeRepository.findByEmail("Lionel.Ronaldo@yahoo.com");

        assertTrue(result.isPresent());
        assertEquals("Lionel Ronaldo", result.get().getName());
        assertEquals("Lionel.Ronaldo@yahoo.com", result.get().getEmail());
    }

    @Test
    void testFindByEmail_WhenEmailDoesNotExist() {
        // Arrange
        Employee employee = new Employee(1,"Sachin Ronaldo", "Sachin.Ronaldo@yahoo.com");
        employeeRepository.save(employee);

        // Act
        Optional<Employee> result = employeeRepository.findByEmail("non.existent@yahoo.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllEmployees() {
        // Arrange
        Employee employee1 = new Employee(1,"Aryan", "Aryan@yahoo.com");
        Employee employee2 = new Employee(1,"Bharath", "Bharath@yahoo.com");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Act
        Iterable<Employee> result = employeeRepository.findAll();

        // Assert
        assertTrue(result.iterator().hasNext());
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        Employee employee = new Employee(1,"Charlie", "charlie@yahoo.com");
        employeeRepository.save(employee);

        // Act
        employee.setName("Charles");
        employee.setEmail("charles@yahoo.com");
        Employee updatedEmployee = employeeRepository.save(employee);

        // Assert
        assertEquals("Charles", updatedEmployee.getName());
        assertEquals("charles@yahoo.com", updatedEmployee.getEmail());
    }
}