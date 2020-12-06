package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Student;

import java.util.List;
import java.util.Set;

public class HibernateStudentDao implements StudentDao {
    final private SessionFactory sessionFactory;

    public HibernateStudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student student) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void create(Set<Student> students) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Student findById(int id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
