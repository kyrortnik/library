package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPool {

    Connection getConnection() throws SQLException;


    boolean releaseConnection(Connection connection);




}
