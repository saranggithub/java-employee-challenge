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
        List<Employee> allEmployees = getFlatListOfEmployee();
        Collections.sort(allEmployees, Employee.SalaryComparator);
        return (int)allEmployees.get(0).getSalary();
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        List<Employee> allEmployees = getFlatListOfEmployee();
        Collections.sort(allEmployees, Employee.SalaryComparator);
        int lastIndex = getLastIndex(allEmployees);
        List<Employee> employees = allEmployees.subList(0, lastIndex);
        return employees.stream().map(Employee::getName).collect(Collectors.toList());
    }

    private List<Employee> getFlatListOfEmployee() {
        return employeeDetailsService.getAllEmployees().values().stream()
                .flatMap(List::stream).collect(Collectors.toList());
    }

    private static int getLastIndex(List<Employee> allEmployees) {
        int lastIndex = 9;
        if(allEmployees.size() < 10) {
            lastIndex = allEmployees.size();
        }
        return lastIndex;
    }
}
