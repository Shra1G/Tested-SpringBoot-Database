package com.team1.caseStudy.team1.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    @Test
    public void testEmployeeConstructor() {
        // Given
        int id = 1;
        String name = "Lionel";
        String email = "Lionel.Ronaldo@gmail.com";

        // When
        Employee employee = new Employee(id, name, email);

        // Then
        assertEquals(id, employee.getId());
        assertEquals(name, employee.getName());
        assertEquals(email, employee.getEmail());
    }

    @Test
    public void testEmployeeNoArgsConstructor() {

        Employee employee = new Employee();

        // When & Then
        assertEquals(0, employee.getId());
        assertEquals(null, employee.getName());
        assertEquals(null, employee.getEmail());
    }

    @Test
    public void testToString() {

        int id = 1;
        String name = "Lionel";
        String email = "Lionel.Ronaldo@gmail.com";
        Employee employee = new Employee(id, name, email);

        // When
        String expectedToString = "Employee(id=" + id + ", name=" + name + ", email=" + email+")";

        // Then
        assertEquals(expectedToString, employee.toString());
    }

    @Test
    public void testSettersAndGetters() {
        // Given
        Employee employee = new Employee();

        // When
        employee.setId(1);
        employee.setName("Sachin");
        employee.setEmail("Sachin.Ronaldo@gmail.com");

        // Then
        assertEquals(1, employee.getId());
        assertEquals("Sachin", employee.getName());
        assertEquals("Sachin.Ronaldo@gmail.com", employee.getEmail());
    }
}