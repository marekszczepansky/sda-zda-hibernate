package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.NamedEntity;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class HibernateBaseDao<T extends NamedEntity> {
    protected final SessionFactory sessionFactory;

    public HibernateBaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(T student) {
        doInTransaction(session -> session.persist(student));
    }

    public void create(Set<T> students) {
        doInTransaction(session -> students.forEach(session::persist));
    }

    public T findById(final Class<T> entityClass, final int id) {
        return getInTransaction(session -> session.find(entityClass, id));
    }

    protected void doInTransaction(Consumer<Session> consumer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            consumer.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    protected <K> K getInTransaction(Function<Session, K> function) {
        Transaction tx = null;
        K result;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            result = function.apply(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        return result;
    }
}
