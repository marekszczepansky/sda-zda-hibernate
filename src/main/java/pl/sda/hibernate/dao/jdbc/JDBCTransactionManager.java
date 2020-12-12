package pl.sda.hibernate.dao.jdbc;

import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.JDBCConfiguration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class JDBCTransactionManager {
    private final JDBCConfiguration jdbcConfiguration;

    public JDBCTransactionManager(JDBCConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public void doInTransaction(Consumer<Connection> connectionConsumer) {
        try (
                final Connection connection = jdbcConfiguration.getConnection()
        ) {
            connection.setAutoCommit(false);

            connectionConsumer.accept(connection);

            connection.commit();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public <T> T getInTransaction(Function<Connection, T> connectionFunction) {
        T result;
        try (
                final Connection connection = jdbcConfiguration.getConnection()
        ) {
            connection.setAutoCommit(false);

            result = connectionFunction.apply(connection);

            connection.commit();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return result;
    }
}
