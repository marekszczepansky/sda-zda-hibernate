package pl.sda.hibernate.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.hibernate.entity.Food;
import pl.sda.hibernate.entity.Ingredient;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Place;

public class HibernateConfiguration {
    private static HibernateConfiguration instance = new HibernateConfiguration();
    private final SessionFactory sessionFactory;

    private HibernateConfiguration() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Food.class)
                .addAnnotatedClass(Ingredient.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Place.class)
                .buildSessionFactory();
    }

    public static HibernateConfiguration getInstance() {
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
