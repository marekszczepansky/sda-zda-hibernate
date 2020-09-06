package pl.sda.hibernate.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.hibernate.entity.Food;
import pl.sda.hibernate.entity.Ingredient;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Place;

public class HibernateConfiguration {
    private final SessionFactory sessionFactory;

    public HibernateConfiguration() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Food.class)
                .addAnnotatedClass(Ingredient.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Place.class)
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
