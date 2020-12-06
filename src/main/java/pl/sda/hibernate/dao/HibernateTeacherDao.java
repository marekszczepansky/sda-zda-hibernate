package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class HibernateTeacherDao extends HibernateBaseDao<Teacher> implements TeacherDao {

    public HibernateTeacherDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Teacher> getAllTeachersForCourseId(final int id) {
        return getInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            return new ArrayList<>(course.getTeachers());
        });
    }

    @Override
    public Teacher findById(final int id) {
        return findById(Teacher.class, id);
    }
}
