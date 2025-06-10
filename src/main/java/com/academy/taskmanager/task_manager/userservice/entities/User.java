package com.academy.taskmanager.task_manager.userservice.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * User entity
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 25)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
