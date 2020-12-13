package pl.sda.hibernate.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "jdbc")
public class JDBCStudentDao implements StudentDao {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL_COLUMN = "email";
    private final JDBCTransactionManager jdbcTransactionManager;

    public JDBCStudentDao(JDBCTransactionManager jdbcTransactionManager) {
        this.jdbcTransactionManager = jdbcTransactionManager;
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            final List<Student> students = new ArrayList<>();
            try {

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from student where course_id=?"
                );

                preparedStatement.setInt(1, id);

                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int studentId = resultSet.getInt(ID_COLUMN);
                    final String studentName = resultSet.getString(NAME_COLUMN);
                    final String email = resultSet.getString(EMAIL_COLUMN);
                    students.add(new Student(studentId, studentName, email));
                }
                return students;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public void create(Student entity) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into student" +
                                "(name, email)" +
                                "values (?, ?)");

                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getEmail());

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public void create(Set<Student> entities) {
        jdbcTransactionManager.doInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into student" +
                                "(name, email)" +
                                "values (?, ?)");

                for(Student entity : entities) {
                    preparedStatement.setString(1, entity.getName());
                    preparedStatement.setString(2, entity.getEmail());

                    preparedStatement.executeUpdate();
                }
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public Student findById(int id) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from student where id = ?"
                );

                preparedStatement.setInt(1, id);

                final ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    final int studentId = resultSet.getInt(ID_COLUMN);
                    final String studentName = resultSet.getString(NAME_COLUMN);
                    final String studentEmail = resultSet.getString(EMAIL_COLUMN);
                    return new Student(studentId, studentName, studentEmail);
                }
                return null;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }

    @Override
    public List<Student> getAll() {
        return jdbcTransactionManager.getInTransaction(connection -> {
            final List<Student> students = new ArrayList<>();
            try {

                final PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from student"
                );

                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int studentId = resultSet.getInt(ID_COLUMN);
                    final String studentName = resultSet.getString(NAME_COLUMN);
                    final String email = resultSet.getString(EMAIL_COLUMN);
                    students.add(new Student(studentId, studentName, email));
                }
                return students;
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
        });
    }
}
