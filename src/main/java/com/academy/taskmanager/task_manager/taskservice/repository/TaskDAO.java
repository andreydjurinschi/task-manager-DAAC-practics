package com.academy.taskmanager.task_manager.taskservice.repository;

import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;

import java.util.List;

public interface TaskDAO {
    List<Task> allTasks();
    Task findTaskById(long id);
    List<Task> getTasksForUser(Long userId);
    List<Task> getTaskByStatus(TaskStatus status);
    void createOrUpdateTask(Task task);
    void deleteTask(long id);
    List<Task> getTasksCreatedByUser(long Id);
}
