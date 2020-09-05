package pl.sda.hibernate.dal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateSessionHelper implements AutoCloseable {
    private final SessionFactory sessionFactory;
    private final Session session;
    private final EntityManager entityManager;

    public HibernateSessionHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.openSession();
        this.entityManager = sessionFactory.createEntityManager();
    }

    void doInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            action.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    <K> K returnInTransaction(Function<Session, K> action) {
        K result = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            result = action.apply(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
