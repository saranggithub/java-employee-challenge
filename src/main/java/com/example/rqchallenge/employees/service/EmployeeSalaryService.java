package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeSalaryService {

    private final FetchEmployeeDetailsService employeeDetailsService;

    public EmployeeSalaryService(@Autowired FetchEmployeeDetailsService employeeDetailsService){
        this.employeeDetailsService = employeeDetailsService;
    }
    public int getHighestSalaryOfEmployees() {
        List<Employee> allEmployees = employeeDetailsService.getAllEmployees().values().stream()
                .flatMap(List::stream).collect(Collectors.toList());
        Collections.sort(allEmployees, Employee.SalaryComparator);
        return (int)allEmployees.get(0).getSalary();
    }
}
