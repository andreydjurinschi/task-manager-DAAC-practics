package com.academy.taskmanager.task_manager.taskservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDTO {

    private String title;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime due_date;

    private Long created_by;

    private Long assignedTo;
}
