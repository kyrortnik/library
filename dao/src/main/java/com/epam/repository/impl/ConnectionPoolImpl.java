package com.epam.repository.impl;

import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;

import java.sql.*;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.repository.utils.DBConstants.*;


public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPoolImpl.class.getName());

    private final ArrayBlockingQueue<Connection> availableConnections = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
    private final ArrayBlockingQueue<Connection> takenConnections = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
    private static boolean driverIsLoaded = false;

    private final String driver;
    private final String url;
    private final String username;
    private final String password;


    public ConnectionPoolImpl(PropertyInitializer propertyInitializer) {

        LOG.info("=======================ConnectionPoolImpl is created");
        this.driver = propertyInitializer.getProperty(DATABASE_DRIVER);
        this.url = propertyInitializer.getProperty(DATABASE_URL);
        this.username = propertyInitializer.getProperty(DATABASE_USERNAME);
        this.password = propertyInitializer.getProperty(DATABASE_PASSWORD);

        try {
            loadJdbcDriver();
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                availableConnections.add(createConnection(url, username, password));
            }
            LOG.info("init availableConnections.size() is " + availableConnections.size());
            LOG.info("init takenConnections.size() is " + takenConnections.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Connection getConnection() throws SQLException, DAOException {
        try {
            if (availableConnections.isEmpty()) {
                if (takenConnections.size() < MAX_POOL_SIZE) {
                    availableConnections.add(createConnection(url, username, password));
                } else {
                    throw new DAOException("Maximum pool size reached, no available connections!");
                }
            }

            Connection connection = availableConnections.take();
            LOG.info("=======take()");

            if (!connection.isValid(MAX_TIMEOUT)) {
                connection = createConnection(url, username, password);
            }
            takenConnections.add(connection);
            LOG.info("=======availableConnections.size() is " + availableConnections.size());
            LOG.info("=======takenConnections.size() is " + takenConnections.size());
            return connection;
        } catch (InterruptedException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public void releaseConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            LOG.info("======= releasing Connection)");
            takenConnections.remove(connection);
            availableConnections.add(connection);
            LOG.info("=======availableConnections.size() is " + availableConnections.size());
            LOG.info("=======takenConnections.size() is " + takenConnections.size());
        }
    }

    private Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(driver);
                driverIsLoaded = true;
                LOG.info("================JDBC driver is loaded");
            } catch (ClassNotFoundException e) {
                LOG.log(Level.SEVERE, "Exception: " + e);
            }
        }
    }

}
