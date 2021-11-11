package com.epam.repository.impl;

import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static com.epam.repository.utils.DBConstants.*;

/**
 * TODO magic numbers to properties
 * */
public class ConnectionPoolImpl implements ConnectionPool {

   private final ArrayList<Connection> availableConnections;
   private final ArrayList<Connection> takenConnections = new ArrayList<>();
   private static boolean driverIsLoaded = false;

   private final String driver;
   private final String url;
   private final String username;
   private final String password;


    public ConnectionPoolImpl(PropertyInitializer propertyInitializer)   {

        availableConnections = new ArrayList<>(INITIAL_POOL_SIZE);
        this.driver = propertyInitializer.getProperty("database.driver");
        this.url = propertyInitializer.getProperty("database.url");
        this.username = propertyInitializer.getProperty("database.username");
        this.password = propertyInitializer.getProperty("database.password");


        try{
            loadJdbcDriver();
            for (int i = 0;i < INITIAL_POOL_SIZE;i++){
                availableConnections.add(createConnection(url, username, password));
            }
        }catch (SQLException e){
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

        Connection connection = availableConnections.remove(availableConnections.size() - 1);

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, username, password);
        }

        takenConnections.add(connection);
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        availableConnections.add(connection);
        takenConnections.remove(connection);
    }

    private Connection createConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(driver);
                driverIsLoaded = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
