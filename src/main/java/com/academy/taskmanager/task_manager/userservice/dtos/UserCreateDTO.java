package com.academy.taskmanager.task_manager.userservice.dtos;

import com.academy.taskmanager.task_manager.userservice.entities.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    private String username;

    private String email;

    private String fullName;

    private UserRole role;

    private LocalDateTime createdAt;

}
