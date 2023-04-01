package com.example.taller01.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String employeeCode;
    private String name;
    private String lastName;
    private String hiringDate;
    private String rol;
    private String password;
    private boolean isActive;
}
