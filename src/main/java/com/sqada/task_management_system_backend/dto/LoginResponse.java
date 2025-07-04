package com.sqada.task_management_system_backend.dto;

import com.sqada.task_management_system_backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
