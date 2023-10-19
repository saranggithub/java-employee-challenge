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
    private static final String RAMESH_HIGH_EARNER = "Ramesh";
    private static final double SECOND_HIGHEST_SALARY = 1001.22d;
    @InjectMocks
    private EmployeeSalaryService employeeSalaryService;
    @Mock
    private FetchEmployeeDetailsService fetchEmployeeService;
    private static final String SARANG = "Sarang";
    @BeforeEach
    void setUp() {
    }

    @Test
    public void getHighestSalarySuccess() {
        Map<String, List<Employee>> employeesWithNameKey = getOutputMap();
        when(fetchEmployeeService.getAllEmployees()).thenReturn(employeesWithNameKey);

        int highestSalaryOfEmployees = employeeSalaryService.getHighestSalaryOfEmployees();

        assertEquals(((int)HIGHEST_SALARY), highestSalaryOfEmployees);
    }

    @Test
    public void getTop10HighestEarningEmployeeNames_Success(){
        Map<String, List<Employee>> employeesWithNameKey = getOutputMap();
        when(fetchEmployeeService.getAllEmployees()).thenReturn(employeesWithNameKey);
        List<String> top10HighestEarningEmployeeNames = employeeSalaryService.getTop10HighestEarningEmployeeNames();
        assertEquals(4,top10HighestEarningEmployeeNames.size());
        assertTrue(RAMESH_HIGH_EARNER.equals(top10HighestEarningEmployeeNames.get(0)));
        assertTrue(SARANG.equals(top10HighestEarningEmployeeNames.get(1)));
    }

    private static Map<String, List<Employee>> getOutputMap() {
        Employee employee1 = new Employee((short)1, SARANG, SECOND_HIGHEST_SALARY, (short)35, "");
        Employee employee2 = new Employee((short)2, RAMESH_HIGH_EARNER, HIGHEST_SALARY, (short)45, "");
        Employee employee3 = new Employee((short)3, "Suresh",500d, (short)55, "");
        Employee employee4 = new Employee((short)4, "Suresh", SECOND_HIGHEST_SALARY, (short)55, "");
        List<Employee> employees1 = new ArrayList<>();
        employees1.add(employee1);

        List<Employee> employees2 = new ArrayList<>();
        employees2.add(employee2);
        employees2.add(employee4);  //since emp2 and 4 having same name

        List<Employee> employees3 = new ArrayList<>();
        employees3.add(employee3);


        Map<String, List<Employee>> employeesWithNameKey = new HashMap<>();
        employeesWithNameKey.put(employee1.getName(), employees1);
        employeesWithNameKey.put(employee2.getName(), employees2);
        employeesWithNameKey.put(employee3.getName(), employees3);
        return employeesWithNameKey;
    }
}