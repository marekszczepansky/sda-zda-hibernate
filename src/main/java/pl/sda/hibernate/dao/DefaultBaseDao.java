package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.BaseEntity;

public abstract class DefaultBaseDao<F extends BaseEntity> implements BaseDao<F> {
    protected final HibernateConfiguration hibernateConfiguration;

    public DefaultBaseDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    @Override
    public void create(F food) {
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

    protected F findById(Class<F> entityType, int id) {
        F result;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {

            result = session.find(entityType, id);

        }
        return result;
    }
}
