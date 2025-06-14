package com.academy.taskmanager.task_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceUtil {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5433/task-manager_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("pass");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
