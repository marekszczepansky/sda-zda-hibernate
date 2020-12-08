package pl.sda.hibernate.configuration;

import org.apache.naming.factory.BeanFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import javax.persistence.EntityManagerFactory;
import java.beans.beancontext.BeanContext;

public class HibernateConfiguration {

    private final SessionFactory sessionFactory;

    public HibernateConfiguration(ConfigurableApplicationContext applicationContext) {

//        sessionFactory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Course.class)
//                .addAnnotatedClass(Student.class)
//                .addAnnotatedClass(Teacher.class)
//                .buildSessionFactory();

        sessionFactory = applicationContext.getBean(EntityManagerFactory.class).unwrap(SessionFactory.class);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
