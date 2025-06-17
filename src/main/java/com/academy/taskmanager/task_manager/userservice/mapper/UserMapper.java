package com.academy.taskmanager.task_manager.userservice.mapper;

import com.academy.taskmanager.task_manager.userservice.dtos.UserCreateDTO;
import com.academy.taskmanager.task_manager.userservice.dtos.UserDTO;
import com.academy.taskmanager.task_manager.userservice.dtos.UserUpdateDTO;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user){
        return new UserDTO(user.getUsername(), user.getEmail(), user.getFullName(), user.getRole(), user.getCreatedAt());
    }

    public User CreateDTOtoEntity(UserCreateDTO userCreateDTO){
        return new User(userCreateDTO.getUsername(),userCreateDTO.getEmail(), userCreateDTO.getFullName(), userCreateDTO.getRole(), userCreateDTO.getCreatedAt());
    }

/*    public User UpdateDTOtoEntity(UserUpdateDTO userUpdateDTO){
        return new User(userUpdateDTO.getId(), userUpdateDTO.getUsername(), userUpdateDTO.getEmail(), userUpdateDTO.getFullName(), userUpdateDTO.getRole());
    }*/
}
