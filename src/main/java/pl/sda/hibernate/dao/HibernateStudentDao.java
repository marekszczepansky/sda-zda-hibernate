package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.hibernate.entity.Student;

public class HibernateStudentDao extends HibernateBaseDao<Student> implements StudentDao {
    public HibernateStudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Student findById(int id) {
        Transaction tx = null;
        Student student;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            student = session.find(Student.class, id);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        return student;
    }
}
