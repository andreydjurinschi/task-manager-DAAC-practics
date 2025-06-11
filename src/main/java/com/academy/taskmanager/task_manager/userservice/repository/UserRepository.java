package com.academy.taskmanager.task_manager.userservice.repository;

import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFullName(String name);
}
