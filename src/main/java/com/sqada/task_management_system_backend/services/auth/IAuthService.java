package com.sqada.task_management_system_backend.services.auth;
import com.sqada.task_management_system_backend.dto.RegisterDTO;
import com.sqada.task_management_system_backend.dto.UserDTO;

public interface IAuthService {
    UserDTO registerUser(RegisterDTO registerDTO);
    boolean hasUserWithEmail(String email);
}
