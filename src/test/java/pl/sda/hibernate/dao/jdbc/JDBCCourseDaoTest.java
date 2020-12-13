package pl.sda.hibernate.dao.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.hibernate.entity.Course;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pl.sda.hibernate.dao.jdbc.JDBCCourseDao.ID_FIELD;
import static pl.sda.hibernate.dao.jdbc.JDBCCourseDao.NAME_FIELD;
import static pl.sda.hibernate.dao.jdbc.JDBCCourseDao.START_DATE_FIELD;

@ExtendWith(MockitoExtension.class)
class JDBCCourseDaoTest {
    private static Course TEST_COURSE1 =
            new Course(1, "course 1", Date.valueOf("2020-12-24"));
    private static Course TEST_COURSE2 =
            new Course(2, "course 2", Date.valueOf("2020-12-31"));

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

//    @Mock   // niemożliwe dla Stub
    private JDBCTransactionManager jdbcTransactionManager;
//    @InjectMocks // niemożliwe dla Stub
    private JDBCCourseDao jdbcCourseDao;

    @BeforeEach
    void setUp() throws SQLException {
        // zastąpione przez stub
//        when(jdbcTransactionManager
//                .getInTransaction(
//                        ArgumentMatchers.<Function<Connection, List<Course>>>any()
//                )
//        ).thenAnswer(invocation -> {
//            Function function = (Function) invocation.getArgument(0);
//            return function.apply(connection);
//        });
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // wymagane dla JDBCTransactionManagerStub
        jdbcTransactionManager = spy(new JDBCTransactionManagerStub(connection));
        jdbcCourseDao = new JDBCCourseDao(jdbcTransactionManager);
    }

    @Test
    void shouldFindByNameLike() throws SQLException {
        // prepare
        final String testName = "testName";
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(ID_FIELD))
                .thenReturn(TEST_COURSE1.getId())
                .thenReturn(TEST_COURSE2.getId());
        when(resultSet.getString(NAME_FIELD))
                .thenReturn(TEST_COURSE1.getName())
                .thenReturn(TEST_COURSE2.getName());
        when(resultSet.getDate(START_DATE_FIELD))
                .thenReturn(Date.valueOf(TEST_COURSE1.getStartDate()))
                .thenReturn(Date.valueOf(TEST_COURSE2.getStartDate()));

        // execute
        final List<Course> actualResult = jdbcCourseDao.findByNameLike(testName);
        // check
        verify(connection, times(1))
                .prepareStatement("select * from course_table where name like ?");
        verifyNoMoreInteractions(connection);
        verify(preparedStatement, times(1))
                .setString(1, testName);
        verify(preparedStatement, times(1)).executeQuery();
        verifyNoMoreInteractions(preparedStatement);
        verify(jdbcTransactionManager, times(1)).getInTransaction(any());
        assertTrue(actualResult.contains(TEST_COURSE1));
        assertTrue(actualResult.contains(TEST_COURSE2));
    }

    private static class JDBCTransactionManagerStub extends JDBCTransactionManager {
        private final Connection connection;

        public JDBCTransactionManagerStub(Connection connection) {
            super(null);
            this.connection = connection;
        }

        @Override
        public <T> T getInTransaction(Function<Connection, T> connectionFunction) {
            return connectionFunction.apply(connection);
        }

        @Override
        public void doInTransaction(Consumer<Connection> connectionConsumer) {
            // TODO: implement stub method
        }
    }
}
