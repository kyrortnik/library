package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class ConnectionPoolImpl implements ConnectionPool {

   private ArrayList<Connection> availableConnections;
   private ArrayList<Connection> takenConnections;
   private static final int INITIAL_POOL_SIZE = 10;
   private static final int MAX_POOL_SIZE = 20;
   private static final int MAX_TIMEOUT = 5;

   PropertyInitializer propertyInitializer = new PropertyInitializer();

   private Properties properties = propertyInitializer.getProperties();

   private  final String URL = propertyInitializer.getProperty("url");
   private  final String USER = propertyInitializer.getProperty("user");
   private  final String PASSWORD = propertyInitializer.getProperty("password");
   private  final String DATABASE_NAME = propertyInitializer.getProperty("database_name");



   public static ConnectionPoolImpl create(String url,String user,String password) throws SQLException {
       ArrayList<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
       for (int i = 0;i < INITIAL_POOL_SIZE;i++){
           pool.add(createConnection(url,user,password));
       }
       return new ConnectionPoolImpl(/*propertyInitializer,pool*/);


   }

    private ConnectionPoolImpl(/*PropertyInitializer propertyInitializer, ArrayList<Connection> availableConnections*/) {

      /* URL = propertyInitializer.getProperty("url");
       USER = propertyInitializer.getProperty("user");
       PASSWORD = propertyInitializer.getProperty("password");
       DATABASE_NAME = propertyInitializer.getProperty("database_name");*/

    }

    /*@Override
    public Connection getConnection(Properties credentials) {
        String url = credentials.getProperty("URL");
        String user = credentials.getProperty("USER");
        String password = credentials.getProperty("PASSWORD");
        String dbName = credentials.getProperty("DATABASE_NAME");


    }*/

    private static Connection createConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /* @Override
    public Connection provide() {
        return null;
    }

    @Override
    public Connection release() {
        return null;
    }*/

    @Override
    public Connection getConnection() throws SQLException {
        if (availableConnections.isEmpty()) {
            if (takenConnections.size() < MAX_POOL_SIZE) {
                availableConnections.add(createConnection(URL, USER, PASSWORD));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = availableConnections.remove(availableConnections.size() - 1);

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(URL, USER, PASSWORD);
        }

        takenConnections.add(connection);
        return connection;
    }



}
