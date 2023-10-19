package com.example.rqchallenge.employees.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Comparator;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Employee {
    public Employee()  {

    }
    @JsonProperty("id")
    private short id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private double salary;
    @JsonProperty("employee_age")
    private short age;
    @JsonProperty("profile_image")
    private String profileImageUrl;
    public static Comparator<Employee> SalaryComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee e1, Employee e2) {
            return (int)(e2.getSalary() - e1.getSalary());
        }
    };

}
