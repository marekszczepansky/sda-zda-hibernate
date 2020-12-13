package pl.sda.hibernate.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.zadanieStream.VisibleForTest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "jdbc")
public class JDBCCourseDao implements CourseDao {

    @VisibleForTest
    static final String ID_FIELD = "id";
    @VisibleForTest
    static final String NAME_FIELD = "name";
    @VisibleForTest
    static final String START_DATE_FIELD = "start_date";
    private final JDBCTransactionManager jdbcTransactionManager;

    public JDBCCourseDao(JDBCTransactionManager jdbcTransactionManager) {
        this.jdbcTransactionManager = jdbcTransactionManager;
    }

    @Override
    public List<Course> findByNameLike(String nameTerm) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            List<Course> courses = new ArrayList<>();
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from course_table where name like ?"
                );

                preparedStatement.setString(1, nameTerm);

                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int courseId = resultSet.getInt(ID_FIELD);
                    final String courseName = resultSet.getString(NAME_FIELD);
                    final Date courseStartDate = resultSet.getDate(START_DATE_FIELD);
                    courses.add(new Course(courseId, courseName, courseStartDate));
                }
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
            return courses;
        });
    }

    @Override
    public void create(Course entity) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into course_table" +
                                "(id, name, start_date)" +
                                "values (?, ?, ?)");

                preparedStatement.setInt(1, entity.getId());
                preparedStatement.setString(2, entity.getName());
                preparedStatement.setDate(3, Date.valueOf(entity.getStartDate()));

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public void create(Set<Course> entities) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into course_table" +
                                "(name, start_date)" +
                                "values (?, ?)");

                for (Course entity : entities) {
                    preparedStatement.setString(1, entity.getName());
                    preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));

                    preparedStatement.executeUpdate();
                }
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public Course findById(int id) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from course_table where id = ?"
                );

                preparedStatement.setInt(1, id);

                final ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    final int courseId = resultSet.getInt(ID_FIELD);
                    final String courseName = resultSet.getString(NAME_FIELD);
                    final Date courseStartDate = resultSet.getDate(START_DATE_FIELD);
                    return new Course(courseId, courseName, courseStartDate);
                }
                return null;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public List<Course> getAll() {
        return jdbcTransactionManager.getInTransaction(connection -> {
            final List<Course> courses = new ArrayList<>();
            try {
                final Statement statement = connection.createStatement();

                final ResultSet resultSet = statement.executeQuery("select * from course_table");

                while (resultSet.next()) {
                    final int courseId = resultSet.getInt(ID_FIELD);
                    final String courseName = resultSet.getString(NAME_FIELD);
                    final Date courseStartDate = resultSet.getDate(START_DATE_FIELD);
                    courses.add(new Course(courseId, courseName, courseStartDate));
                }
                return courses;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }
}
