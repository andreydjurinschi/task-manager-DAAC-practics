package com.academy.taskmanager.task_manager.validator;

import org.springframework.stereotype.Component;

@Component
public class ValidateFields {
    public boolean checkIfNotEmpty(String data){
        return data != null && !data.isEmpty();
    }

    public boolean checkLength(String data, int minLength, int maxLength){
        return data.length() >= minLength && data.length() <= maxLength;
    }
}
