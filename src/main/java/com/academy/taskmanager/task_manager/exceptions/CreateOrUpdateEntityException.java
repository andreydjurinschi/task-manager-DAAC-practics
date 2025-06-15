package com.academy.taskmanager.task_manager.exceptions;


import java.util.List;

public class CreateOrUpdateEntityException extends Exception{
    private final List<String> errors;

    public CreateOrUpdateEntityException(List<String> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public String getErrors() {
        return errors.toString();
    }
}
