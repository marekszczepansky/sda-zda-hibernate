package pl.sda.hibernate.dal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateCourseDAO implements CourseDAO {

    private final SessionFactory sessionFactory;

    public HibernateCourseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Course course) {
        doInTransaction(session -> session.persist(course));
    }

    @Override
    public void create(List<Course> courses) {
        doInTransaction(session -> courses.forEach(session::persist));
    }

    @Override
    public Optional<Course> findById(int id) {
        return Optional.ofNullable(
                returnInTransaction(session -> session.find(Course.class, id))
        );
    }

    @Override
    public List<Course> findByNameLike(String term) {
        return returnInTransaction(session -> {
            final Query<Course> query = session.createQuery("from Course where name like :nameparam", Course.class);
            query.setParameter("nameparam", term);
            return query.list();
        });
    }

    @Override
    public List<Course> findByName(String name) {
        return returnInTransaction(session -> {
            final Query<Course> query = session.createQuery("from Course where name = :nameparam", Course.class);
            query.setParameter("nameparam", name);
            return query.list();
        });
    }

    private void doInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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

    private <K> K returnInTransaction(Function<Session, K> action) {
        K result = null;
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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
}
