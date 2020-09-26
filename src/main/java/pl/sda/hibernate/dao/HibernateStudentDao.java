package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Student;

public class HibernateStudentDao extends HibernateBaseDao<Student> implements StudentDao {
    public HibernateStudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Student findById(int id) {
        return findById(Student.class, id);
    }
}
