package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Student;

import java.util.List;

public class HibernateStudentDao extends HibernateBaseDao<Student> implements StudentDao {

    public HibernateStudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
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

}
