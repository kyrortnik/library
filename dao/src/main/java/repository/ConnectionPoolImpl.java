package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConnectionPoolImpl implements ConnectionPool {

   private final ArrayList<Connection> availableConnections;
   private final ArrayList<Connection> takenConnections = new ArrayList<>();
   private static final int INITIAL_POOL_SIZE = 10;
   private static final int MAX_POOL_SIZE = 20;
   private static final int MAX_TIMEOUT = 5;
    private final String url;
    private final String username;
    private final String password;



    public static ConnectionPoolImpl create(String url,String username,String password) throws SQLException {

        ArrayList<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0;i < INITIAL_POOL_SIZE;i++){
            connectionPool.add(createConnection(url,username,password));
        }

        return new ConnectionPoolImpl(url,username,password,connectionPool);


    }


    private ConnectionPoolImpl(String url,String username,String password, ArrayList<Connection> availableConnections) {
         this.url = url;
         this.username = username;
         this.password = password;
         this.availableConnections = availableConnections;
    }


    private static Connection createConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
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
    public boolean releaseConnection(Connection connection) {
        availableConnections.add(connection);
        return takenConnections.remove(connection);
    }


}
