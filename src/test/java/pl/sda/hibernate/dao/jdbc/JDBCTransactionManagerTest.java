package pl.sda.hibernate.dao.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.hibernate.configuration.JDBCConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JDBCTransactionManagerTest {

    @Mock
    private Connection connection;
    @Mock
    private JDBCConfiguration jdbcConfiguration;
    @InjectMocks
    private JDBCTransactionManager jdbcTransactionManager;

    @BeforeEach
    void setUp() throws SQLException {
        when(jdbcConfiguration.getConnection()).thenReturn(connection);
    }

    @Test
    void shouldDoInTransaction() throws SQLException {
        jdbcTransactionManager.doInTransaction(conn -> {
            try {
                verify(conn, times(1)).setAutoCommit(false);
                verifyNoMoreInteractions(conn);
            } catch (SQLException throwables) {
                fail();
            }
        });

        verify(connection, times(1)).commit();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    void shouldDoInTransactionCloseConnectionOnSqlException() throws SQLException {
        doThrow(SQLException.class).when(connection).commit();

        final RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            jdbcTransactionManager.doInTransaction(conn -> {
            });
        });

        assertEquals(SQLException.class, runtimeException.getCause().getClass());
        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    void shouldGetInTransaction() throws SQLException {
        final String test_value = "test_value";

        final String result = jdbcTransactionManager.getInTransaction(conn -> {
            try {
                verify(conn, times(1)).setAutoCommit(false);
                verifyNoMoreInteractions(conn);
                return test_value;
            } catch (SQLException throwables) {
                fail();
            }
            return null;
        });

        assertEquals(test_value, result);
        verify(connection, times(1)).commit();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    void shouldGetInTransactionCloseConnectionOnSqlException() throws SQLException {
        doThrow(SQLException.class).when(connection).commit();

        final RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            jdbcTransactionManager.doInTransaction(conn -> {
            });
        });

        assertEquals(SQLException.class, runtimeException.getCause().getClass());
        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }
}
