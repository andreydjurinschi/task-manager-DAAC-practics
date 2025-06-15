package com.academy.taskmanager.task_manager.taskservice.controller;

import com.academy.taskmanager.task_manager.exceptions.CreateOrUpdateEntityException;
import com.academy.taskmanager.task_manager.exceptions.EntityNotFoundException;
import com.academy.taskmanager.task_manager.taskservice.dtos.CreateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.TaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.UpdateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;
import com.academy.taskmanager.task_manager.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return new ResponseEntity<>(service.allTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            TaskDTO task = service.findTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTasksForUser(@PathVariable Long userId) {
        try {
            List<TaskDTO> tasks = service.getTasksForUser(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable TaskStatus status) {
        return new ResponseEntity<>(service.getTasksByStatus(status), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO taskDTO) {
        try {
            service.createTask(taskDTO);
            return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
        } catch (CreateOrUpdateEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO taskDTO) {
        try {
            service.updateTask(id, taskDTO);
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } catch (CreateOrUpdateEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam Long id) {
        try {
            service.deleteTask(id);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/created-by/{userId}")
    public ResponseEntity<?> getTasksCreatedBy(@PathVariable Long userId) {
        try {
            List<TaskDTO> tasks = service.getTasksCreatedBy(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<?> changeTaskToOtherUser(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            service.changeTaskToOtherUser(taskId, userId);
            return new ResponseEntity<>("Task assigned successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{taskId}/status/{status}")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long taskId, @PathVariable TaskStatus status) {
        try {
            service.changeTaskStatus(taskId, status);
            return new ResponseEntity<>("Task status updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}