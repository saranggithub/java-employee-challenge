package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public interface IEmployeeController {

    @GetMapping("/search/all")
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException, ServiceException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) throws ServiceException;

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

    class IEmployeeControllerImpl implements IEmployeeController {
        @Override
        public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
            return null;
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
}
