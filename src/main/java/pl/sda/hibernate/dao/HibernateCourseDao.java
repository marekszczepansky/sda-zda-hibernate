package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateCourseDao implements CourseDao {
    final private SessionFactory sessionFactory;

    public HibernateCourseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Course course) {
        doInTransaction(session -> session.persist(course));
    }

    @Override
    public void create(Set<Course> courses) {
        doInTransaction(session -> courses.forEach(session::persist));
    }

    @Override
    public Course findById(final int id) {
        return getInTransaction(session -> session.find(Course.class, id));
    }

    @Override
    public List<Course> findByNameLike(String nameTerm) {

        return getInTransaction(session -> {
            final Query<Course> courseQuery = session.createQuery(
                    "from Course c where name like :nameparam",
                    Course.class);
            courseQuery.setParameter("nameparam", nameTerm);

            return courseQuery.getResultList();
        });
    }

    private void doInTransaction(Consumer<Session> consumer) {
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

    private <K> K getInTransaction(Function<Session, K> function) {
        Transaction tx = null;
        K result = null;
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
