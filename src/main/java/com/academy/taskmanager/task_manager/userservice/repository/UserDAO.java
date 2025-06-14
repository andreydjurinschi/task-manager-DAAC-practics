package com.academy.taskmanager.task_manager.userservice.repository;


import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.userservice.entities.User;

import java.util.List;

public interface UserDAO {
    void createOrUpdate(User user);
    void delete(User user);
    User findById(long id);
    List<User> findAll();
    List<Task> getCompletedTasksByUser(Long Id);
}
