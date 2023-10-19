package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.CustomError;
import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class EmployeeDefinitionService {
    private static final String FAILED_TO_CREATE_EMPLOYEE = "Failed to create employee ";
    private static final String NAME = "name";
    private static final String SALARY = "salary";
    private static final String AGE = "age";
    private static final String CREATING_EMPLOYEE = "Creating employee ";
    private static final String STATUS = "status";
    private final RestTemplate restTemplate;
    private final String createEmployeePath;
    private final String url;

    private final Logger logger = LoggerFactory.getLogger(EmployeeDefinitionService.class);
    public EmployeeDefinitionService(@Autowired RestTemplate restTemplate,
                                     @Value("${employees.portal}") final String url,
                                     @Value("${create.employee.path}") final String createEmployeePath) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.createEmployeePath = createEmployeePath;

    }
    public String create(Map<String, Object> employeeDetails) {

        Employee employee = getEmployee(employeeDetails);
        logger.info(CREATING_EMPLOYEE + employee);
        HttpEntity<Employee> entity = new HttpEntity<>(employee);
        LinkedHashMap<String, String> createdEmployee;

        try {
            createdEmployee = restTemplate.postForObject(url + createEmployeePath, entity, LinkedHashMap.class);
        } catch (HttpClientErrorException exception) {
            logger.error(FAILED_TO_CREATE_EMPLOYEE + exception);
            throw new ServiceException(new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(),
                    exception.getLocalizedMessage()));
        }

        if(null == createdEmployee) {
            throw new ServiceException(new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    FAILED_TO_CREATE_EMPLOYEE, ""));
        }
        return createdEmployee.get(STATUS);
    }

    private static Employee getEmployee(Map<String, Object> employeeDetails) {
        Employee employee = new Employee();
        employee.setName(employeeDetails.get(NAME).toString());
        employee.setSalary(Double.valueOf(employeeDetails.get(SALARY).toString()));
        employee.setAge(Short.valueOf(employeeDetails.get(AGE).toString()));
        return employee;
    }
}
