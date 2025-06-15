package com.academy.taskmanager.task_manager.taskservice.dtos;

import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String title;

    private String description;

    private TaskStatus status = TaskStatus.New;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime due_date;

    private Long created_by;

    private Long assignedTo;

}

