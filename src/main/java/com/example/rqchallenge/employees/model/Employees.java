package com.example.rqchallenge.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Employees {
    public Employees() {};
    private List<Employee> employees;
}
