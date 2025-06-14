package com.academy.taskmanager.task_manager.userservice.repository;

import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO{

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createOrUpdate(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findById(long id) {
        try(var session = sessionFactory.openSession()) {
            String sql = "from User where id = :id";
            return session.createQuery(sql, User.class).setParameter("id", id).uniqueResult();
        }
    }

    /**
     * Find all users
     * @return List<User></>
     */
    @Override
    public List<User> findAll() {
        try(var session = sessionFactory.openSession()){
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public List<Task> getCompletedTasksByUser(Long Id) {
        return List.of();
    }
}
