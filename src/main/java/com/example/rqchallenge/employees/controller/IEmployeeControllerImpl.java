package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.FetchEmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class IEmployeeControllerImpl implements IEmployeeController {
    @Autowired
    private FetchEmployeeDetailsService fetchEmployeeDetailsService;

    @Override
    @GetMapping("/search/all")
    public ResponseEntity<List<Employee>> getAllEmployees() throws ServiceException {
        List<Employee> values = fetchEmployeeDetailsService.getAllEmployees().values().stream()
                .flatMap(List::stream).collect(Collectors.toList());
        return new ResponseEntity<>(values, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return null;
    }
}
