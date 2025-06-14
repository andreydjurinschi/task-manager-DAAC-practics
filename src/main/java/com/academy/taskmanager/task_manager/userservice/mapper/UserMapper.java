package com.academy.taskmanager.task_manager.userservice.mapper;

import com.academy.taskmanager.task_manager.userservice.dtos.UserDTO;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user){
        return new UserDTO(user.getUsername(), user.getEmail(), user.getFullName(), user.getRole(), user.getCreatedAt());
    }
}
