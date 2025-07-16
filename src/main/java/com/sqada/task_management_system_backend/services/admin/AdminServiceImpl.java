package com.sqada.task_management_system_backend.services.admin;

import com.sqada.task_management_system_backend.dto.TaskDTO;
import com.sqada.task_management_system_backend.dto.UserDTO;
import com.sqada.task_management_system_backend.entities.Task;
import com.sqada.task_management_system_backend.entities.User;
import com.sqada.task_management_system_backend.enums.TaskStatus;
import com.sqada.task_management_system_backend.enums.UserRole;
import com.sqada.task_management_system_backend.repositories.TaskRepository;
import com.sqada.task_management_system_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public AdminServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Optional<User> optUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optUser.isPresent()){
            Task task = new Task();
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setDueDate(taskDTO.getDueDate());
            task.setPriority(taskDTO.getPriority());
            task.setUser(optUser.get());
            return taskRepository.save(task).getTaskDTO();
        }
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate)
                        .reversed())
                .map(
                Task::getTaskDTO
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        Optional<Task> optTask = taskRepository.findById(taskId);
        return optTask.map(Task::getTaskDTO).orElseThrow(() -> new RuntimeException("Can't get the task by id"));
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO, Long taskId) {
        Optional<Task> optTask = taskRepository.findById(taskId);
        Optional<User> optUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optTask.isPresent() && optUser.isPresent()){
            Task existingTask = optTask.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDTO.getTaskStatus())));
            existingTask.setUser(optUser.get());
            return taskRepository.save(existingTask).getTaskDTO();
        }
        return null;
    }

    private TaskStatus mapStringToTaskStatus(String status){
        return switch (status){
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
