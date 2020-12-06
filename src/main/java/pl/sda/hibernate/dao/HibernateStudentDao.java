package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateStudentDao implements StudentDao {
    final private SessionFactory sessionFactory;

    public HibernateStudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student student) {
        doInTransaction(session -> session.persist(student));
    }

    @Override
    public void create(Set<Student> students) {
        doInTransaction(session -> students.forEach(session::persist));
    }

    @Override
    public Student findById(int id) {
        return getInTransaction(session -> session.find(Student.class, id));
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        return getInTransaction(session -> {
            final Query<Student> courseQuery = session.createQuery(
                    "from Student s where s.course.id = :courseIdParam",
                    Student.class);
            courseQuery.setParameter("courseIdParam", id);

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
