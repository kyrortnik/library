package com.epam.repository;

import com.epam.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    Connection getConnection() throws SQLException, DAOException;

    void releaseConnection(Connection connection);
}
