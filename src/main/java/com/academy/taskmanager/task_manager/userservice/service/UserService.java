package com.academy.taskmanager.task_manager.userservice.service;

import com.academy.taskmanager.task_manager.userservice.dtos.UserDTO;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import com.academy.taskmanager.task_manager.userservice.mapper.UserMapper;
import com.academy.taskmanager.task_manager.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * User service
 */

@Service
public class UserService {

    private UserMapper mapper;
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> findAll(){
        List<User> allUsers = repository.findAll();
        List<UserDTO> userDtos = new ArrayList<>();
        for(var user : allUsers){
           userDtos.add(mapper.toDto(user));
        }
        return userDtos;
    }

    public UserDTO getUserById(Long Id){
        User user = repository.findById(Id).orElseThrow(() -> new RuntimeException("asd"));\
        return mapper.toDto(user);

    }


}
