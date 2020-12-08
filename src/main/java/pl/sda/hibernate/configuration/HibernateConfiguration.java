package pl.sda.hibernate.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import javax.persistence.EntityManagerFactory;

@Component
public class HibernateConfiguration {

    public final SessionFactory sessionFactory;

    public HibernateConfiguration(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
