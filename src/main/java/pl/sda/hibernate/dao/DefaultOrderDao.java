package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Place;

public class DefaultOrderDao implements OrderDao {
    private final HibernateConfiguration hibernateConfiguration;

    public DefaultOrderDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    @Override
    public void create(Order order) {
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }
    }

    @Override
    public Order findById(int id) {
        Order result;
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();

            result = session.find(Order.class, id);

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
