package com.sqada.task_management_system_backend.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    UserDetailsService userDetailService();
}
