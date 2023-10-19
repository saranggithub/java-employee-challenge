package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeSalaryServiceTest {

    private static final double HIGHEST_SALARY = 2111.22d;
    @InjectMocks
    private EmployeeSalaryService employeeSalaryService;
    @Mock
    private FetchEmployeeDetailsService fetchEmployeeService;
    private static final String SARANG = "Sarang";
    @BeforeEach
    void setUp() {
    }

    @Test
    public void testException() {
        Map<String, List<Employee>> employeesWithNameKey = getOutputMap();

        when(fetchEmployeeService.getAllEmployees()).thenReturn(employeesWithNameKey);
        int highestSalaryOfEmployees = employeeSalaryService.getHighestSalaryOfEmployees();
        assertEquals(((int)HIGHEST_SALARY), highestSalaryOfEmployees);
    }

    private static Map<String, List<Employee>> getOutputMap() {
        Employee employee1 = new Employee((short)1, SARANG,1001.22d, (short)35, "");
        Employee employee2 = new Employee((short)2, "Ramesh", HIGHEST_SALARY, (short)45, "");
        Employee employee3 = new Employee((short)3, "Suresh",500d, (short)55, "");
        List<Employee> employees1 = new ArrayList<>();
        employees1.add(employee1);
        List<Employee> employees2 = new ArrayList<>();
        employees2.add(employee2);
        List<Employee> employees3 = new ArrayList<>();
        employees3.add(employee3);
        Map<String, List<Employee>> employeesWithNameKey = new HashMap<>();
        employeesWithNameKey.put(employee1.getName(), employees1);
        employeesWithNameKey.put(employee2.getName(), employees2);
        employeesWithNameKey.put(employee3.getName(), employees3);
        return employeesWithNameKey;
    }
}