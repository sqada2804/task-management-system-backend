package com.sqada.task_management_system_backend.dto;

import com.sqada.task_management_system_backend.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
}
