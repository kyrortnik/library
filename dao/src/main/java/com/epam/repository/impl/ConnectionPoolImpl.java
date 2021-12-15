package com.epam.repository.impl;

import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

import static com.epam.repository.utils.DBConstants.*;


public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPoolImpl.class);

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
            LOG.error(e.getMessage());
        }

    }


    @Override
    public Connection getConnection() throws  DAOException {
        try {

            Connection connection = availableConnections.take();
            LOG.info("=======take()");
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
                LOG.error("Error while loading JDBC Driver");
            }
        }
    }

}
