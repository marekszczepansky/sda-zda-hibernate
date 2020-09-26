package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Teacher;

public class HibernateTeacherDao extends HibernateBaseDao<Teacher> implements TeacherDao {
    public HibernateTeacherDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Teacher findById(int id) {
        return findById(Teacher.class, id);
    }
}
