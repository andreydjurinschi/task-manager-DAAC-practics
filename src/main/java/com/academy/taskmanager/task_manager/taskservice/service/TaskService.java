package com.academy.taskmanager.task_manager.taskservice.service;

import com.academy.taskmanager.task_manager.exceptions.CreateOrUpdateEntityException;
import com.academy.taskmanager.task_manager.exceptions.EntityNotFoundException;
import com.academy.taskmanager.task_manager.taskservice.dtos.CreateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.TaskDto;
import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;
import com.academy.taskmanager.task_manager.taskservice.mapper.TaskMapper;
import com.academy.taskmanager.task_manager.taskservice.repository.TaskDaoImpl;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import com.academy.taskmanager.task_manager.userservice.repository.UserDaoImpl;
import com.academy.taskmanager.task_manager.validator.ValidateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskDaoImpl taskDao;
    private final UserDaoImpl userDao;
    private final TaskMapper  taskMapper;
    private final ValidateFields validator;


    @Autowired
    public TaskService(TaskDaoImpl taskDao, UserDaoImpl userDao, TaskMapper taskMapper, ValidateFields validateFields) {
        this.taskDao = taskDao;
        this.userDao = userDao;
        this.taskMapper = taskMapper;
        this.validator = validateFields;
    }

    public List<TaskDto> allTasks(){
        List<Task> allTasks = taskDao.allTasks();
        List<TaskDto> taskDtos = new ArrayList<TaskDto>();
        for (Task task : allTasks) {
            taskDtos.add(taskMapper.toDto(task));
        }
        return taskDtos;
    }

    public TaskDto findTaskById(long Id) {
        Task task = taskDao.findTaskById(Id);
        if(task == null) {
            throw new EntityNotFoundException("Task with id " + Id + " not found");
        }
        return taskMapper.toDto(task);
    }

    public List<TaskDto> findTasksByUser(long userId) {
        User user = userDao.findById(userId);
        if(user == null) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        List<Task> userTasks = taskDao.getTasksForUser(userId);
        List<TaskDto> dtos = new ArrayList<>();
        for(Task task : userTasks) {
            dtos.add(taskMapper.toDto(task));
        }
        return dtos;
    }

    public List<TaskDto> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskDao.getTaskByStatus(status);
        List<TaskDto> dtos = new ArrayList<>();
        for(Task task : tasks) {
            dtos.add(taskMapper.toDto(task));
        }
        return dtos;
    }

    public void createTask(CreateTaskDTO taskDTO) throws CreateOrUpdateEntityException {
        List<String> errors = new ArrayList<>();
        if(!validator.checkIfNotEmpty(taskDTO.getTitle()) ||
            !validator.checkLength(taskDTO.getTitle(), 5, 25)){
            errors.add("Title must be between 5 and 25 characters");
        }
        if(!validator.checkIfNotEmpty(taskDTO.getDescription()) ||
                !validator.checkLength(taskDTO.getDescription(), 5, 150)){
            errors.add("Title must be between 5 and 25 characters");
        }
        if(taskDTO.getCreated_by() == null || taskDTO.getAssignedTo() == null){
            errors.add("Users must ...");
        }
        if(taskDTO.getDue_date() == null){
            errors.add("Due date baby ...");
        }

        User userCreator = userDao.findById(taskDTO.getCreated_by());
        User assigner = userDao.findById(taskDTO.getAssignedTo());
        if(userCreator == null){
            errors.add("Creator does not exists");
        }
        if(assigner == null){
            errors.add("Assigner does not exists");
        }
        if(!errors.isEmpty()){
            throw new CreateOrUpdateEntityException(errors);
        }
        taskDao.createOrUpdateTask(taskMapper.createTaskToEntity(taskDTO, userCreator, assigner));
    }


}
