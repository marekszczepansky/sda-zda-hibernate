package pl.sda.hibernate.dal;

import org.hibernate.SessionFactory;
import pl.sda.hibernate.entity.Student;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v8.0
 */
public class HibernateStudentDAO implements StudentDAO {


    private final HibernateSessionHelper hibernateSessionHelper;

    public HibernateStudentDAO(SessionFactory sessionFactory) {
        hibernateSessionHelper =  new HibernateSessionHelper(sessionFactory);
    }

    @Override
    public void create(List<Student> students) {
        hibernateSessionHelper.doInTransaction(session -> students.forEach(session::persist));
    }
}
