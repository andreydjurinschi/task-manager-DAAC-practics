package com.academy.taskmanager.task_manager.userservice.repository;


import com.academy.taskmanager.task_manager.userservice.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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


    /**
     * CREATES OR UPDATES THE USER
     * @param user
     */
    @Override
    public void createOrUpdate(User user) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();

            try {
                if (user.getId() == null) {
                    session.persist(user);
                } else {
                    session.merge(user);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }

    }

    /**
     * Deletes an user
     * @param Id
     */
    @Override
    public void delete(Long Id)  {
        var session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try(session) {
            User user = findById(Id);
            session.remove(user);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
    }

    /**
     * Find user by his id
     * @param id
     * @return user user
     */
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
    public User findByUsername(String username) {
        try(var session = sessionFactory.openSession()){
            String sql = "from User u where u.username = :username";
            return session.createQuery(sql, User.class).setParameter("username", username).uniqueResult();
        }
    }



}
