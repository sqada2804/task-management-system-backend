package com.sqada.task_management_system_backend.repositories;

import com.sqada.task_management_system_backend.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
