package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.ServiceException;
import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDefinitionServiceTest {

    @InjectMocks
    private EmployeeDefinitionService employeeDefinitionService;
    @Mock
    private RestTemplate restTemplate;
    private static final String SARANG = "Sarang";

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(employeeDefinitionService, "url", "https://dummy.create.com" );
        ReflectionTestUtils.setField(employeeDefinitionService, "createEmployeePath", "/api/v1/create");
    }
    @Test
    public void createEmployeeSuccess() {
        Map<String, Object> employeeDetails = getEmployeeDetails();
        List<LinkedHashMap> employeesList = getEmployeesList(SARANG);
        LinkedHashMap<String, Object> stringResponseEntity = getObjectResponseEntity(employeesList);;

        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(stringResponseEntity);

        assertEquals("success", employeeDefinitionService.create(employeeDetails));
        verify(restTemplate, times(1)).postForObject(anyString(), any(), any());

    }

    @Test
    public void createEmployeeException() {
        Map<String, Object> employeeDetails = getEmployeeDetails();
        when(restTemplate.postForObject(anyString(), any(), any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThrows(ServiceException.class, () -> employeeDefinitionService.create(employeeDetails));
    }

    private static Map<String, Object> getEmployeeDetails() {
        Map<String, Object> employeeDetails = new HashMap<>();
        employeeDetails.put("name", SARANG);
        employeeDetails.put("salary", "1234.56");
        employeeDetails.put("age", "35");
        return employeeDetails;
    }

    private static LinkedHashMap<String,Object> getObjectResponseEntity(List<LinkedHashMap> employees) {
        LinkedHashMap<String, Object> responseObj = new LinkedHashMap<>();
        responseObj.put("status", "success");
        responseObj.put("data", employees);
        return responseObj;
    }

    private static List<LinkedHashMap> getEmployeesList(String employee1Name) {
        LinkedHashMap<String, String> employee1 = new LinkedHashMap<>();
        employee1.put("id", "1");
        employee1.put("employee_name", employee1Name);
        employee1.put("employee_age", "35");
        employee1.put("employee_salary", "1001.22");
        employee1.put("profile_image", "");
        return Arrays.asList(employee1);
    }
}
