package com.academy.taskmanager.task_manager.taskservice.service;

import com.academy.taskmanager.task_manager.exceptions.CreateOrUpdateEntityException;
import com.academy.taskmanager.task_manager.exceptions.EntityNotFoundException;
import com.academy.taskmanager.task_manager.taskservice.dtos.CreateTaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.TaskDTO;
import com.academy.taskmanager.task_manager.taskservice.dtos.UpdateTaskDTO;
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

    public List<TaskDTO> allTasks(){
        List<Task> allTasks = taskDao.allTasks();
        List<TaskDTO> taskDTOS = new ArrayList<TaskDTO>();
        for (Task task : allTasks) {
            taskDTOS.add(taskMapper.toDto(task));
        }
        return taskDTOS;
    }

    public TaskDTO findTaskById(long Id) {
        Task task = taskDao.findTaskById(Id);
        if(task == null) {
            throw new EntityNotFoundException("Task with id " + Id + " not found");
        }
        return taskMapper.toDto(task);
    }

    public List<TaskDTO> getTasksForUser(long userId) {
        User user = userDao.findById(userId);
        if(user == null) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        List<Task> userTasks = taskDao.getTasksForUser(userId);
        List<TaskDTO> dtos = new ArrayList<>();
        for(Task task : userTasks) {
            dtos.add(taskMapper.toDto(task));
        }
        return dtos;
    }

    public List<TaskDTO> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskDao.getTaskByStatus(status);
        List<TaskDTO> dtos = new ArrayList<>();
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

    public void updateTask(Long Id, UpdateTaskDTO dto) throws CreateOrUpdateEntityException {
        List<String> errors = new ArrayList<>();

        Task task = taskDao.findTaskById(Id);
        if (task == null) {
            throw new EntityNotFoundException("Task with id " + Id + " not found");
        }

        if (dto.getTitle() != null && (!validator.checkIfNotEmpty(dto.getTitle()) ||
                !validator.checkLength(dto.getTitle(), 5, 25))) {
            errors.add("Title must be between 5 and 25 characters");
        }

        if (dto.getDescription() != null && (!validator.checkIfNotEmpty(dto.getDescription()) ||
                !validator.checkLength(dto.getDescription(), 5, 150))) {
            errors.add("Description must be between 5 and 150 characters");
        }

        if (dto.getDue_date() != null && dto.getDue_date().isBefore(LocalDateTime.now())) {
            errors.add("Due date must be in the future");
        }

        if (dto.getAssignedTo() != null) {
            User newAssignee = userDao.findById(dto.getAssignedTo());
            if (newAssignee == null) {
                errors.add("Assignee with id " + dto.getAssignedTo() + " not found");
            } else {
                task.setAssignedTo(newAssignee);
            }
        }

        if (!errors.isEmpty()) {
            throw new CreateOrUpdateEntityException(errors);
        }

        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }

        if (dto.getDue_date() != null) {
            task.setDue_date(dto.getDue_date());
        }

        task.setUpdated_at(LocalDateTime.now());

        taskDao.createOrUpdateTask(task);
    }

    public void deleteTask(Long Id){
        Task task = taskDao.findTaskById(Id);
        if(task == null){
            throw new EntityNotFoundException("Not found");
        }
        taskDao.deleteTask(Id);
    }

    public List<TaskDTO> getTasksCreatedBy(long userId){
        User user = userDao.findById(userId);
        if(user == null){
            throw new EntityNotFoundException("Assignee with id " + userId + " not found");
        }
        List<Task> tasks = taskDao.getTasksCreatedByUser(userId);
        List<TaskDTO> dtos = new ArrayList<>();
        for (var task : tasks){
            dtos.add(taskMapper.toDto(task));
        }
        return dtos;
    }

    public void changeTaskToOtherUser(long taskId, long newUserId){
        Task task = taskDao.findTaskById(taskId);
        User user = userDao.findById(newUserId);
        if(task == null){
            throw new EntityNotFoundException("Task with id " + taskId + " not found");
        }
        if(user == null){
            throw new EntityNotFoundException("Task with id " + newUserId + " not found");
        }
        taskDao.changeTaskToOtherUser(taskId, newUserId);
        }

    public void changeTaskStatus(Long taskId, TaskStatus status){
        Task task = taskDao.findTaskById(taskId);
        if(task == null){
            throw new EntityNotFoundException("Task with id " + taskId + " not found");
        }
        taskDao.changeTaskStatus(taskId, status);
    }
}



