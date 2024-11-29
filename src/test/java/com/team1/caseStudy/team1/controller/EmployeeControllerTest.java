package com.team1.caseStudy.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team1.caseStudy.team1.entity.Employee;
import com.team1.caseStudy.team1.service.EmployeeService;
import com.team1.caseStudy.team1.service.ExportService;
import com.team1.caseStudy.team1.service.ImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ExportService exportService;

    @Mock
    private ImportService importService;

    @InjectMocks
    private EmployeeController employeeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testSaveEmployee() throws Exception {
        Employee employee = new Employee(1, "Lionel", "Lionel.Ronaldo@gmail.com");

        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lionel"))
                .andExpect(jsonPath("$.email").value("Lionel.Ronaldo@gmail.com"));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = new Employee(1, "Lionel", "Lionel.Ronaldo@gmail.com");

        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lionel"))
                .andExpect(jsonPath("$.email").value("Lionel.Ronaldo@gmail.com"));
    }

    @Test
    public void testGetEmployeeByIdNotFound() throws Exception {
        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = new Employee(1, "Lionel", "Lionel.Ronaldo@gmail.com");
        Employee employee2 = new Employee(2, "Ankit", "Ankit.Ronaldo@gmail.com");

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Lionel"))
                .andExpect(jsonPath("$[0].email").value("Lionel.Ronaldo@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Ankit"))
                .andExpect(jsonPath("$[1].email").value("Ankit.Ronaldo@gmail.com"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee existingEmployee = new Employee(1, "Lionel", "Lionel.Ronaldo@gmail.com");
        Employee updatedEmployee = new Employee(1, "Lionel Updated", "Lionel.updated@gmail.com");

        when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lionel Updated"))
                .andExpect(jsonPath("$.email").value("Lionel.updated@gmail.com"));
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        Employee updatedEmployee = new Employee(1, "Lionel Updated", "Lionel.updated@gmail.com");

        when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Simulate that export service works without issues
        mockMvc.perform(get("/employees/export"))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV exported successfully!"));
    }

    @Test
    public void testImportToCSV() throws Exception {
        // Simulate that import service works without issues
        mockMvc.perform(get("/employees/import"))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV import successfully!"));
    }

    @Test
    public void testGetEmployeeByEmail() throws Exception {
        Employee employee = new Employee(1, "Lionel", "Lionel.Ronaldo@gmail.com");

        when(employeeService.getEmployeeByEmail("Lionel.Ronaldo@gmail.com")).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/email/Lionel.Ronaldo@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lionel"))
                .andExpect(jsonPath("$.email").value("Lionel.Ronaldo@gmail.com"));
    }

    @Test
    void testGetEmployeeById_Success() {
        Employee employee = new Employee(1, "Ram Laxman", "Ram.Laxman@example.com");

        Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetEmployeeById_BadRequest() {
        Mockito.doThrow(new IllegalArgumentException("Invalid ID"))
                .when(employeeService).getEmployeeById(1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetEmployeeById_InternalServerError() {
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(employeeService).getEmployeeById(1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetEmployeeByEmail_Success() {
        Employee existingEmployee = new Employee(1, "Ram Laxman", "Ram.Laxman@example.com");

        Mockito.when(employeeService.getEmployeeByEmail("Ram.Laxman@example.com")).thenReturn(Optional.of(existingEmployee));

        ResponseEntity<Employee> response = employeeController.getEmployeeByEmail("Ram.Laxman@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ram.Laxman@example.com", response.getBody().getEmail());
    }

    @Test
    void testGetEmployeeByEmail_NotFound() {
        Mockito.when(employeeService.getEmployeeByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeByEmail("nonexistent@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetEmployeeByEmail_BadRequest() {
        Mockito.when(employeeService.getEmployeeByEmail("invalidemail")).thenThrow(new IllegalArgumentException("Invalid email"));

        ResponseEntity<Employee> response = employeeController.getEmployeeByEmail("invalidemail");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetEmployeeByEmail_InternalServerError() {
        Mockito.when(employeeService.getEmployeeByEmail("error@example.com")).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<Employee> response = employeeController.getEmployeeByEmail("error@example.com");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee existingEmployee = new Employee(1, "Mohit Nair", "Mohit.Nair@example.com");
        Employee updatedEmployee = new Employee(1, "Tom Nair", "Tom.Nair@example.com");

        Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeService.saveEmployee(existingEmployee)).thenReturn(updatedEmployee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1, updatedEmployee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tom Nair", response.getBody().getName());
        assertEquals("Tom.Nair@example.com", response.getBody().getEmail());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.updateEmployee(1, new Employee());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateEmployee_BadRequest() {
        Mockito.doThrow(new IllegalArgumentException("Invalid input"))
                .when(employeeService).getEmployeeById(1);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1, new Employee());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateEmployee_InternalServerError() {
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(employeeService).getEmployeeById(1);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1, new Employee());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
