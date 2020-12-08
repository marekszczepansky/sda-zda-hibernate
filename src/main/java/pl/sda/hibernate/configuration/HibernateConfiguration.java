package pl.sda.hibernate.configuration;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

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
