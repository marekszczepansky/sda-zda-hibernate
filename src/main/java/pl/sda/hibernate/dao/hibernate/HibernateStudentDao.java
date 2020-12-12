package pl.sda.hibernate.dao.hibernate;

import org.hibernate.query.Query;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.entity.Student;

import java.util.List;

@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "hibernate", matchIfMissing = true)
public class HibernateStudentDao extends HibernateBaseDao<Student> implements StudentDao {

    public HibernateStudentDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Student findById(int id) {
        return findById(Student.class, id);
    }

    @Override
    public List<Student> getAll() {
        return getAll(Student.class);
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
