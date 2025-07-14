package com.sqada.task_management_system_backend.services.admin;

import com.sqada.task_management_system_backend.dto.TaskDTO;
import com.sqada.task_management_system_backend.dto.UserDTO;
import com.sqada.task_management_system_backend.entities.Task;
import com.sqada.task_management_system_backend.entities.User;

import java.util.List;

public interface IAdminService {
    List<UserDTO> getUsers();
    TaskDTO createTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasks();
    void deleteTask(Long taskId);
}
