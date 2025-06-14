package com.academy.taskmanager.task_manager.userservice.dtos;

import com.academy.taskmanager.task_manager.userservice.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String username;

    private String email;

    private String fullName;

    private UserRole role;

    private LocalDateTime createdAt;

}
