package com.sqada.task_management_system_backend.controller.admin;

import com.sqada.task_management_system_backend.dto.TaskDTO;
import com.sqada.task_management_system_backend.services.admin.IAdminService;
import com.sqada.task_management_system_backend.services.jwt.IUserService;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin("*")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO taskDTO1 = adminService.createTask(taskDTO);
        if(taskDTO1 == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO1);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(adminService.getAllTasks());
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        adminService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.ok(adminService.getTaskById(taskId));
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long taskId){
        TaskDTO updatedTask = adminService.updateTask(taskDTO, taskId);
        if(updatedTask == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedTask);
    }
}
