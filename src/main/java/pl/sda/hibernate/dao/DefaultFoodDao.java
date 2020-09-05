package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Food;

public class DefaultFoodDao implements FoodDao {
    private final HibernateConfiguration hibernateConfiguration;

    public DefaultFoodDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    @Override
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

    @Override
    public Food findById(int id) {
        Food result;
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();

            result = session.find(Food.class, id);

            transaction.commit();
        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }
        return result;

    }
}
