package pl.sda.hibernate.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

public class HibernateConfiguration {
    private static HibernateConfiguration INSTANCE = new HibernateConfiguration();

    private final SessionFactory sessionFactory;

    public HibernateConfiguration() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static HibernateConfiguration getInstance() {
        return INSTANCE;
    }
}
