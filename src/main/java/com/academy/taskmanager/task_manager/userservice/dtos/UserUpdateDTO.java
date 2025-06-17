package com.academy.taskmanager.task_manager.userservice.dtos;

import com.academy.taskmanager.task_manager.userservice.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;

}
