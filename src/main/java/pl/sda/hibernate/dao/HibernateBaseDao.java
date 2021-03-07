package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.entity.NamedEntity;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class HibernateBaseDao<T extends NamedEntity> implements BaseDao<T> {
    final public SessionFactory sessionFactory;

    public HibernateBaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T entity) {
        doInTransaction(session -> session.persist(entity));
    }

    protected void doInTransaction(Consumer<Session> task) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            task.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }

    }

    protected T findById(Class<T> entityType, int id) {
        return getInTransaction(session -> session.find(entityType, id));
    }

    protected <K> K getInTransaction(Function<Session, K> task) {
        Transaction tx = null;
        K entity;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            entity = task.apply(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        return entity;
    }
}
