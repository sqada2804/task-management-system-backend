package com.sqada.task_management_system_backend.repositories;

import com.sqada.task_management_system_backend.entities.User;
import com.sqada.task_management_system_backend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String username);
    Optional<User> findByUserRole(UserRole userRole);
}
