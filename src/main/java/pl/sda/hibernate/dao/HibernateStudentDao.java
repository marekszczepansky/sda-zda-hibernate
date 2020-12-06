package pl.sda.hibernate.dao;

import org.hibernate.query.Query;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Student;

import java.util.List;

public class HibernateStudentDao extends HibernateBaseDao<Student> implements StudentDao {

    public HibernateStudentDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Student findById(int id) {
        return findById(Student.class, id);
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
