package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.CustomError;
import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FetchEmployeeDetailsService {
    private static final String ERROR_WHILE_FETCHING_EMPLOYEES = "Error while fetching employees";
    private static final String RESPONSE_KEY = "data";
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    private final RestTemplate restTemplate;
    private final String allEmployeePath;
    private final String url;
    private final Logger logger = LoggerFactory.getLogger(FetchEmployeeDetailsService.class);

    public FetchEmployeeDetailsService(@Autowired RestTemplate restTemplate,
                                       @Value("${employees.portal}") final String url,
                                       @Value("${all.employee.path}") final String allEmployeePath) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.allEmployeePath = allEmployeePath;
    }

    public Map<String, List<Employee>> getAllEmployees() throws ServiceException {
        ResponseEntity<Object> employeeResponseEntity;
        Map<String, List<Employee>> employeesMapNameAsKey;

        try {
            employeeResponseEntity = restTemplate.getForEntity(url + allEmployeePath, Object.class);
        } catch (HttpClientErrorException exception) {
            logger.error("Exception in fetching response " + exception);
            throw new ServiceException(new CustomError(500, exception.getMessage(),
                    exception.getLocalizedMessage()));
        }

        handleEmployeeNotFound(employeeResponseEntity);
        handleErrorneousResponses(employeeResponseEntity);
        List<LinkedHashMap> employeesObjects =
                (List<LinkedHashMap>) ((LinkedHashMap<String, Object>) employeeResponseEntity.getBody()).get(RESPONSE_KEY);

        employeesMapNameAsKey = getEmployeesMapWithNameAsKey(employeesObjects);

        return employeesMapNameAsKey;
    }

    private Map<String, List<Employee>> getEmployeesMapWithNameAsKey(List<LinkedHashMap> employeesObjects) {
        Map<String, List<Employee>> employeesMapNameAsKey = new HashMap<>(1);
        ObjectMapper mapper = new ObjectMapper();
        if(!employeesObjects.isEmpty()) {
            employeesMapNameAsKey = employeesObjects.stream().map(emp -> mapper.convertValue(emp, Employee.class))
                    .collect(Collectors.groupingBy(Employee::getName,
                                Collectors.mapping(Function.identity(), Collectors.toList())));
        }
        return employeesMapNameAsKey;
    }

    private void handleErrorneousResponses(ResponseEntity<Object> employeeResponseEntity) throws ServiceException {
        if (!(employeeResponseEntity.getStatusCode() == HttpStatus.OK)) {
            logger.error(ERROR_WHILE_FETCHING_EMPLOYEES);
            throw new ServiceException(new CustomError(500,ERROR_WHILE_FETCHING_EMPLOYEES, ""));
        }
    }

    private void handleEmployeeNotFound(ResponseEntity<Object> employeeResponseEntity) throws ServiceException {
        if(employeeResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            logger.warn(EMPLOYEE_NOT_FOUND);
            throw new ServiceException(new CustomError(404,EMPLOYEE_NOT_FOUND, ""));
        }
    }

    public List<Employee> search(String name) throws ServiceException {
        Map<String, List<Employee>> employeesMapWithNameAsKey = getAllEmployees();
        return employeesMapWithNameAsKey.get(name);
    }
}
