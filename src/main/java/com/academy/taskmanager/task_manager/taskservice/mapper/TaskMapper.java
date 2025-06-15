package com.academy.taskmanager.task_manager.taskservice.mapper;

import com.academy.taskmanager.task_manager.taskservice.dtos.CreateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.TaskDto;
import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        return new TaskDto(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreated_at(), task.getUpdated_at(), task.getDue_date(), task.getCreated_by().getId(), task.getAssignedTo().getId());
    }

    public Task createTaskToEntity(CreateTaskDTO dto, User creator, User assignedTo) {
        return new Task(dto.getTitle(), dto.getDescription(), dto.getStatus(), dto.getCreated_at(), dto.getUpdated_at(), dto.getDue_date(), creator, assignedTo);
    }
}
