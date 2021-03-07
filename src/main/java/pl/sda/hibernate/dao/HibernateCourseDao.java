package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;

import java.util.List;

public class HibernateCourseDao extends HibernateBaseDao<Course> implements CourseDao {

    public HibernateCourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Course findById(int id) {
        return findById(Course.class, id);
    }

    @Override
    public Course updateNameById(int id, String newName) {
        return getInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            course.setName(newName);
            return course;
        });
    }

    @Override
    public List<Course> findByNameLike(String term) {
        return getInTransaction(session -> {
            final Query<Course> courseQuery = session.createQuery(
                    "select c from Course c where name like :nameparam",
                    Course.class);
            courseQuery.setParameter("nameparam", term);
            return courseQuery.getResultList();
        });
    }
}
