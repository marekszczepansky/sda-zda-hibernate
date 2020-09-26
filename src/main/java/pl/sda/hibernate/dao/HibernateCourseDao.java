package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Course;

public class HibernateCourseDao extends HibernateBaseDao<Course> implements CourseDao {

    public HibernateCourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Course findById(int id) {
        return findById(Course.class, id);
    }
}
