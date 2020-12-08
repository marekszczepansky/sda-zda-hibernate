package pl.sda.hibernate;


import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.sda.hibernate.configuration.HibernateConfiguration;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringApp.class, args);
        HibernateConfiguration.sessionFactory =  applicationContext.getBean(EntityManagerFactory.class).unwrap(SessionFactory.class);
        HibernateApp.mainOld();
    }
}
