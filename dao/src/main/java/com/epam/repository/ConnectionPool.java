package com.epam.repository;

import com.epam.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    /**
     * Provides JDBC Connection from Connection Pool
     *
     * @return Connection from Connection Pool
     * @throws SQLException throws SQLException
     * @throws DAOException throws DAOException
     */
    Connection getConnection() throws SQLException, DAOException;

    /**
     * Puts unused Connection back to Connection Pool
     *
     * @param connection, connection to put back to Connection Pool
     */
    void releaseConnection(Connection connection);
}
