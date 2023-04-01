package com.example.taller01.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    String employeeCode;
    String password;
}
