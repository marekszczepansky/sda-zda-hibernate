package pl.sda.hibernate.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.configuration.JDBCConfiguration;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.dao.hibernate.HibernateTeacherDao;
import pl.sda.hibernate.entity.Teacher;

import java.util.List;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "jdbc")
public class JDBCTeacherDao implements TeacherDao {

    private final JDBCConfiguration jdbcConfiguration;
    private final HibernateConfiguration hibernateConfiguration;
    private final HibernateTeacherDao hibernateTeacherDao;

    public JDBCTeacherDao(JDBCConfiguration jdbcConfiguration, HibernateConfiguration hibernateConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
        this.hibernateConfiguration = hibernateConfiguration;
        this.hibernateTeacherDao = new HibernateTeacherDao(hibernateConfiguration);
    }

    @Override
    public List<Teacher> getAllTeachersForCourseId(int id) {
        return hibernateTeacherDao.getAllTeachersForCourseId(id);
    }

    @Override
    public void create(Teacher entity) {
        hibernateTeacherDao.create(entity);
    }

    @Override
    public void create(Set<Teacher> entities) {
        hibernateTeacherDao.create(entities);
    }

    @Override
    public Teacher findById(int id) {
        return hibernateTeacherDao.findById(id);
    }

    @Override
    public List<Teacher> getAll() {
        return hibernateTeacherDao.getAll();
    }
}
