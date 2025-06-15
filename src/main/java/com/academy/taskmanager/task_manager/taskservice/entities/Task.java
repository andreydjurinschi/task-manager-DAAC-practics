package com.academy.taskmanager.task_manager.taskservice.entities;

import com.academy.taskmanager.task_manager.userservice.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * Task entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", length = 1000)
    private String description;

    @Column
    private TaskStatus status = TaskStatus.New;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "due_date")
    private LocalDateTime due_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User created_by;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @PrePersist
    private void onCreate(){
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdated(){
        updated_at = LocalDateTime.now();
    }

    public Task(String title, String description, TaskStatus status, LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime due_date, User created_by, User assignedTo) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.due_date = due_date;
        this.created_by = created_by;
        this.assignedTo = assignedTo;
    }
}
