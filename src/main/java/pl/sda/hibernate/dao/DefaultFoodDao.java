package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Food;

public class DefaultFoodDao {
    private final HibernateConfiguration hibernateConfiguration;

    public DefaultFoodDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    public void create(Food food) {
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();
            session.persist(food);
            transaction.commit();

        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }
    }
}
