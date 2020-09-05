package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Ingredient;

public class DefaultIngredientDao {
    private final HibernateConfiguration hibernateConfiguration;

    public DefaultIngredientDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    void create(Ingredient ingredient) {

        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();
            session.persist(ingredient);
            transaction.commit();
        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }

    }

    Ingredient findById(int id) {
        return null;
    }
}
