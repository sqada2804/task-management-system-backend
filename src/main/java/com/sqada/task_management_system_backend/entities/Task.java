package com.sqada.task_management_system_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sqada.task_management_system_backend.dto.TaskDTO;
import com.sqada.task_management_system_backend.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private TaskStatus taskStatus;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public TaskDTO getTaskDTO(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        taskDTO.setTitle(title);
        taskDTO.setDescription(description);
        taskDTO.setDueDate(dueDate);
        taskDTO.setTaskStatus(taskStatus);
        taskDTO.setEmployeeId(user.getId());
        taskDTO.setEmployeeName(user.getUsername());
        return taskDTO;
    }
}
