package com.academy.taskmanager.task_manager.taskservice.repository;

import com.academy.taskmanager.task_manager.taskservice.entities.Task;
import com.academy.taskmanager.task_manager.taskservice.entities.TaskStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDAO {


    private final SessionFactory sessionFactory;

    @Autowired
    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> allTasks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task", Task.class).list();
        }
    }

    @Override
    public Task findTaskById(long id) {
        try(Session session = sessionFactory.openSession()) {
            String sql = "from Task where id = :id";
            return session.createQuery(sql, Task.class).setParameter("id", id).uniqueResult();
        }
    }

    @Override
    public List<Task> getTasksForUser(Long userId) {
        try(Session session = sessionFactory.openSession()) {
            String sql = "from Task where assignedTo = :userId";
            return session.createQuery(sql, Task.class).setParameter("userId", userId).list();

        }
    }

    @Override
    public List<Task> getTaskByStatus(TaskStatus status) {
        try(Session session = sessionFactory.openSession()) {
            String sql = "from Task where status = :status";
            return session.createQuery(sql, Task.class).setParameter("status", status).list();
        }
    }

    @Override
    public void createOrUpdateTask(Task task) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        try(session){
            if(task.getId() == null){
                session.persist(task);
            }else{
                session.merge(task);
            }
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
    }

    @Override
    public void deleteTask(long id) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        try(session){
            Task task = findTaskById(id);
            session.remove(task);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
    }

    @Override
    public List<Task> getTasksCreatedByUser(long Id) {
        try(Session session = sessionFactory.openSession()) {
            String sql = "from Task where created_by = :userId";
            return session.createQuery(sql, Task.class).setParameter("userId", Id).list();
        }
    }

    @Override
    public void changeTaskToOtherUser(long taskId, long newUserId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try(session) {
            session.createQuery("update Task t set t.assignedTo.Id = :newUserId where t.id = :taskId", Task.class)
                    .setParameter("newUserId", newUserId)
                    .setParameter("taskId", taskId).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void changeTaskStatus(long taskId, TaskStatus newStatus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try(session) {
            session.createQuery("update Task t set t.status = :newStatus where t.id = :taskId", Task.class).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
