package pl.sda.hibernate.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.configuration.JDBCConfiguration;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.dao.hibernate.HibernateTeacherDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "jdbc")
public class JDBCTeacherDao implements TeacherDao {

    private final JDBCTransactionManager jdbcTransactionManager;
    private final JDBCConfiguration jdbcConfiguration;
    private final HibernateConfiguration hibernateConfiguration;
    private final HibernateTeacherDao hibernateTeacherDao;

    public JDBCTeacherDao(JDBCTransactionManager jdbcTransactionManager,
                          JDBCConfiguration jdbcConfiguration,
                          HibernateConfiguration hibernateConfiguration) {
        this.jdbcTransactionManager = jdbcTransactionManager;
        this.jdbcConfiguration = jdbcConfiguration;
        this.hibernateConfiguration = hibernateConfiguration;
        this.hibernateTeacherDao = new HibernateTeacherDao(hibernateConfiguration);
    }

    @Override
    public List<Teacher> getAllTeachersForCourseId(int id) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            final List<Teacher> teachers = new ArrayList<>();
            try {

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select teacher1_.* " +
                                "    from " +
                                "        course_teacher teachers0_ " +
                                "    inner join " +
                                "        teacher teacher1_ " +
                                "            on teachers0_.teacher_id=teacher1_.id " +
                                "    where " +
                                "        teachers0_.course_id=?"
                );

                preparedStatement.setInt(1, id);

                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int teacherId = resultSet.getInt("id");
                    final String teacherName = resultSet.getString("name");
                    final String subject = resultSet.getString("subject");
                    teachers.add(new Teacher(teacherId, teacherName, subject));
                }
                return teachers;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public void create(Teacher entity) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into teacher" +
                                "(id, name, subject)" +
                                "values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                final PreparedStatement joinStatement = connection.prepareStatement(
                        "insert into course_teacher" +
                                "(teacher_id, course_id)" +
                                "values (?, ?)");

                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getSubject());
                preparedStatement.executeUpdate();

                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next() && entity.getCourses().size() > 0) {
                    final int teacherId = generatedKeys.getInt(1);
                    for (Course course : entity.getCourses()) {
                        joinStatement.setInt(1, teacherId);
                        joinStatement.setInt(2, course.getId());
                        joinStatement.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public void create(Set<Teacher> entities) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into teacher" +
                                "(id, name, subject)" +
                                "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                final PreparedStatement joinStatement = connection.prepareStatement(
                        "insert into course_teacher" +
                                "(teacher_id, course_id)" +
                                "values (?, ?)");


                for (Teacher entity : entities) {
                    preparedStatement.setInt(1, entity.getId());
                    preparedStatement.setString(2, entity.getName());
                    preparedStatement.setString(3, entity.getSubject());

                    preparedStatement.executeUpdate();

                    final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next() && entity.getCourses().size() > 0) {
                        final int teacherId = generatedKeys.getInt(1);
                        for (Course course : entity.getCourses()) {
                            joinStatement.setInt(1, teacherId);
                            joinStatement.setInt(2, course.getId());
                            joinStatement.executeUpdate();
                        }
                    }
                }

            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public Teacher findById(int id) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            try {
                final Statement statement = connection.createStatement();

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from teacher where id = ?"
                );

                preparedStatement.setInt(1, id);

                final ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    final int teacherId = resultSet.getInt("id");
                    final String teacherName = resultSet.getString("name");
                    final String subject = resultSet.getString("subject");
                    return new Teacher(teacherId, teacherName, subject);
                }
                return null;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public List<Teacher> getAll() {
        return jdbcTransactionManager.getInTransaction(connection -> {
            final List<Teacher> teachers = new ArrayList<>();
            try {
                final Statement statement = connection.createStatement();

                final ResultSet resultSet = statement.executeQuery("select * from teacher");

                while (resultSet.next()) {
                    final int teacherId = resultSet.getInt("id");
                    final String teacherName = resultSet.getString("name");
                    final String subject = resultSet.getString("subject");
                    teachers.add(new Teacher(teacherId, teacherName, subject));
                }
                return teachers;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }
}
