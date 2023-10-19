package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private static final String SARANG = "Sarang";
    private static final String RAMESH = "Ramesh";
    @InjectMocks
    private FetchEmployeeDetailsService fetchEmployeeDetailsService;
    @Mock
    private RestTemplate restTemplate;
    private String url = "https://dummy.dummy.com";
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fetchEmployeeDetailsService, "url", url);
    }

    @Test
    public void allEmployeesEmptyList() throws ServiceException {
        String path = "/api/v1/employees";
        ResponseEntity<Object> stringResponseEntity = getObjectResponseEntity(Collections.emptyList());

        when(restTemplate.getForEntity(url + path, Object.class)).thenReturn(stringResponseEntity);
        fetchEmployeeDetailsService.getAllEmployees();
        assertEquals(0, fetchEmployeeDetailsService.getAllEmployees().size());
    }

    @Test
    public void allEmployeesServerErrorResponse(){
        String path = "/api/v1/employees";
        Map<String, Object> responseObj = new LinkedHashMap<>();
        ResponseEntity<Object> stringResponseEntity = new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(url + path, Object.class)).thenReturn(stringResponseEntity);
        assertThrows(ServiceException.class, () -> fetchEmployeeDetailsService.getAllEmployees());
    }

    @Test
    public void allEmployeesSuccessResponseWithEmployeeList() throws ServiceException {
        String path = "/api/v1/employees";
        List<LinkedHashMap> employeesList = getEmployeesList(SARANG, RAMESH);
        ResponseEntity<Object> stringResponseEntity = getObjectResponseEntity(employeesList);;

        when(restTemplate.getForEntity(url + path, Object.class)).thenReturn(stringResponseEntity);
        assertEquals(2, fetchEmployeeDetailsService.getAllEmployees().size());
    }

    @Test
    public void searchByNameSuccessResponse() throws ServiceException {
        String path = "/api/v1/employees";
        Employee employee1 = new Employee((short)1, SARANG,1001.22d, (short)35, "");
        List<LinkedHashMap> employeesList = getEmployeesList(SARANG, RAMESH);
        ResponseEntity<Object> stringResponseEntity = getObjectResponseEntity(employeesList);;
        when(restTemplate.getForEntity(url + path, Object.class)).thenReturn(stringResponseEntity);
        List<Employee> searchedEmpList = fetchEmployeeDetailsService.search(SARANG);
        assertEquals(1, searchedEmpList.size());
        assertTrue(employee1.equals(searchedEmpList.get(0)));
    }

    @Test
    public void searchByNameSameNameEmployee() throws ServiceException {
        String path = "/api/v1/employees";
        Employee employee1 = new Employee((short)1, SARANG,1001.22d, (short)35, "");
        List<LinkedHashMap> employeesList = getEmployeesList(SARANG, SARANG);
        ResponseEntity<Object> stringResponseEntity = getObjectResponseEntity(employeesList);;
        when(restTemplate.getForEntity(url + path, Object.class)).thenReturn(stringResponseEntity);
        List<Employee> searchedEmpList = fetchEmployeeDetailsService.search(SARANG);
        assertEquals(2, searchedEmpList.size());
    }

    private static List<LinkedHashMap> getEmployeesList(String employee1Name, String employee2Name) {
        LinkedHashMap<String,String> employee1 = new LinkedHashMap<>();
        employee1.put("id", "1");
        employee1.put("employee_name", employee1Name);
        employee1.put("employee_age", "35");
        employee1.put("employee_salary", "1001.22");
        employee1.put("profile_image", "");

        LinkedHashMap<String,String> employee2 = new LinkedHashMap<>();
        employee2.put("id", "2");
        employee2.put("employee_name", employee2Name);
        employee2.put("employee_age", "55");
        employee2.put("employee_salary", "4233.55");
        employee2.put("profile_image", "");

        List<LinkedHashMap> employeesList = new ArrayList<>(2);
        employeesList.add(employee1);
        employeesList.add(employee2);
        return employeesList;
    }

    private static ResponseEntity<Object> getObjectResponseEntity(List<LinkedHashMap> employees) {
        ObjectMapper mapper = new ObjectMapper();
//        List<LinkedHashMap> collect = employees.stream().map(emp -> mapper.convertValue(Employee.class, LinkedHashMap.class)).collect(Collectors.toList());
        Map<String, Object> responseObj = new LinkedHashMap<>();
        responseObj.put("status", "success");
        responseObj.put("data", employees);
        responseObj.put("message","Successfully! All records has been fetched.");
        ResponseEntity<Object> stringResponseEntity = new ResponseEntity<>(responseObj, HttpStatus.OK);
        return stringResponseEntity;
    }

}