package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.entity.NamedEntity;

public abstract class HibernateBaseDao<T extends NamedEntity> implements BaseDao<T> {
    final public SessionFactory sessionFactory;

    public HibernateBaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            session.persist(entity);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    protected T findById(Class<T> entityType, int id) {
        Transaction tx = null;
        T course;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            course = session.find(entityType, id);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        return course;
    }
}
