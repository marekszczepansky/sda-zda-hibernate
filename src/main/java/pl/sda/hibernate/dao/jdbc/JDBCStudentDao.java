package pl.sda.hibernate.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.configuration.JDBCConfiguration;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.hibernate.HibernateStudentDao;
import pl.sda.hibernate.entity.Student;

import java.util.List;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "jdbc")
public class JDBCStudentDao implements StudentDao {

    private final JDBCConfiguration jdbcConfiguration;
    private final HibernateConfiguration hibernateConfiguration;
    private final HibernateStudentDao hibernateStudentDao;

    public JDBCStudentDao(JDBCConfiguration jdbcConfiguration, HibernateConfiguration hibernateConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
        this.hibernateConfiguration = hibernateConfiguration;
        this.hibernateStudentDao = new HibernateStudentDao(hibernateConfiguration);
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        return hibernateStudentDao.findAllByCourseId(id);
    }

    @Override
    public void create(Student entity) {
        hibernateStudentDao.create(entity);
    }

    @Override
    public void create(Set<Student> entities) {
        hibernateStudentDao.create(entities);
    }

    @Override
    public Student findById(int id) {
        return hibernateStudentDao.findById(id);
    }

    @Override
    public List<Student> getAll() {
        return hibernateStudentDao.getAll();
    }
}
