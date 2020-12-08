package pl.sda.hibernate.dao;

import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

@Component
public class HibernateTeacherDao extends HibernateBaseDao<Teacher> implements TeacherDao {

    public HibernateTeacherDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
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

    @Override
    public List<Teacher> getAll() {
        return getAll(Teacher.class);
    }
}
