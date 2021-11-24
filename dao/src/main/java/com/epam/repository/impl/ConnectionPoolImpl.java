package com.epam.repository.impl;

import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.repository.utils.DBConstants.*;


public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger log = Logger.getLogger(ConnectionPoolImpl.class.getName());

   private final ArrayBlockingQueue<Connection> availableConnections;
   private final ArrayBlockingQueue<Connection> takenConnections = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
   private static boolean driverIsLoaded = false;

   private final String driver;
   private final String url;
   private final String username;
   private final String password;


    public ConnectionPoolImpl(PropertyInitializer propertyInitializer)   {

        log.info("=======================ConnectionPoolImpl is created");
        availableConnections = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        this.driver = propertyInitializer.getProperty(DATABASE_DRIVER);
        this.url = propertyInitializer.getProperty(DATABASE_URL);
        this.username = propertyInitializer.getProperty(DATABASE_USERNAME);
        this.password = propertyInitializer.getProperty(DATABASE_PASSWORD);


        try{
            loadJdbcDriver();
            for (int i = 0;i < INITIAL_POOL_SIZE;i++){
                availableConnections.add(createConnection(url, username, password));
            }
            log.info("init availableConnections.size() is " + availableConnections.size());
            log.info("init takenConnections.size() is " + takenConnections.size());
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: "+ e);
            e.printStackTrace();
        }

    }


    @Override
    public Connection getConnection() throws SQLException {
        if (availableConnections.isEmpty()) {
            if (takenConnections.size() < MAX_POOL_SIZE) {
                availableConnections.add(createConnection(url, username, password));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = availableConnections.remove();
        log.info("=======take()");

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, username, password);
        }

        takenConnections.add(connection);
        log.info("=======availableConnections.size() is " + availableConnections.size());
        log.info("=======takenConnections.size() is " + takenConnections.size());
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        log.info("======= releasing Connection)");
        takenConnections.remove(connection);
        availableConnections.add(connection);
        log.info("=======availableConnections.size() is " + availableConnections.size());
        log.info("=======takenConnections.size() is " + takenConnections.size());
    }

    private Connection createConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(driver);
                driverIsLoaded = true;
                log.info("================JDBC driver is loaded");
            } catch (ClassNotFoundException e) {
                log.log(Level.SEVERE,"Exception: "+ e);
            }
        }
    }

}
