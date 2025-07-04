package com.sqada.task_management_system_backend.services.admin;

import com.sqada.task_management_system_backend.dto.UserDTO;
import com.sqada.task_management_system_backend.entities.User;
import com.sqada.task_management_system_backend.enums.UserRole;
import com.sqada.task_management_system_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService{

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDTO)
                .collect(Collectors.toList());
    }
}
