package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.entity.Course;

public class HibernateCourseDao extends HibernateBaseDao<Course> implements CourseDao {

    public HibernateCourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Course findById(int id) {
        Transaction tx = null;
        Course course;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            course = session.find(Course.class, id);

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
