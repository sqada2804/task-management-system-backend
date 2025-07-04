package com.sqada.task_management_system_backend.services.auth;

import com.sqada.task_management_system_backend.dto.RegisterDTO;
import com.sqada.task_management_system_backend.dto.UserDTO;
import com.sqada.task_management_system_backend.entities.User;
import com.sqada.task_management_system_backend.enums.UserRole;
import com.sqada.task_management_system_backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createAdminUser(){
        Optional<User> optUser = userRepository.findByUserRole(UserRole.ADMIN);
        if(optUser.isEmpty()){
            User user =  new User();
            user.setEmail("admin@test.com");
            user.setUsername("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully!!!");
        } else {
            System.out.println("Admin account already exists");
        }
    }

    @Override
    public UserDTO registerUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.getPassword()));
        user.setUsername(registerDTO.getName());
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser = userRepository.save(user);
        return createdUser.getUserDTO();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }


}
