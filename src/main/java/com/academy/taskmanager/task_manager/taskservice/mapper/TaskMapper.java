package com.academy.taskmanager.task_manager.taskservice.mapper;

import com.academy.taskmanager.task_manager.taskservice.dtos.CreateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.TaskDTO;
import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        return new TaskDTO(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreated_at(), task.getUpdated_at(), task.getDue_date(), task.getCreated_by().getId(), task.getAssignedTo().getId());
    }

    public Task createTaskToEntity(CreateTaskDTO dto, User creator, User assignedTo) {
        return new Task(dto.getTitle(), dto.getDescription(), TaskStatus.New, dto.getCreated_at(), LocalDateTime.now(), dto.getDue_date(), creator, assignedTo);
    }


}
