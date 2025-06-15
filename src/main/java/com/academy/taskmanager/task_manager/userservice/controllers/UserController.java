package com.academy.taskmanager.task_manager.userservice.controllers;

import com.academy.taskmanager.task_manager.exceptions.CreateOrUpdateEntityException;
import com.academy.taskmanager.task_manager.exceptions.EntityNotFoundException;
import com.academy.taskmanager.task_manager.userservice.dtos.UserCreateDTO;
import com.academy.taskmanager.task_manager.userservice.dtos.UserDTO;
import com.academy.taskmanager.task_manager.userservice.dtos.UserUpdateDTO;
import com.academy.taskmanager.task_manager.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try{
            UserDTO user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        try{
            userService.updateUser(userUpdateDTO);
            return new ResponseEntity<>(userUpdateDTO,HttpStatus.OK);
        } catch (CreateOrUpdateEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        try{
            userService.createUser(userCreateDTO);
            return new ResponseEntity<>(userCreateDTO,HttpStatus.CREATED);
        } catch (CreateOrUpdateEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        try{
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully",HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
