package com.academy.taskmanager.task_manager.taskservice.service;

import com.academy.taskmanager.task_manager.taskservice.repository.TaskDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskDaoImpl taskDao;

    @Autowired
    public TaskService(TaskDaoImpl taskDao) {
        this.taskDao = taskDao;
    }


}
