package com.academy.taskmanager.task_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
		"com.academy.taskmanager.task_manager.taskservice.entities",
		"com.academy.taskmanager.task_manager.userservice.entities"
})

public class TaskManagerApplication {
//http://localhost:8080/swagger-ui/index.html#/
	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
