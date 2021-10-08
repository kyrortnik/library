package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {


    public DataBaseConfig() {
        loadJdbcDriver();
//        properties = propertyInitializer.getProperties();
    }

    private static final String DRIVER = "database.driver";
  /*  URL = "database.url",
    DATABASE_NAME = "database.name",
    USERNAME = "database.username",
    PASSWORD = "database.password";*/

//    private final Properties properties;
    private boolean driverIsLoaded = false;

    // obsolete getConnection
   /* public Connection getConnection() throws SQLException {
        loadJdbcDriver();
        Connection connection;
        Properties credentials = new Properties();
        credentials.setProperty("user", properties.getProperty(USERNAME));
        credentials.setProperty("password", properties.getProperty(PASSWORD));
        connection = DriverManager.getConnection(URL + DATABASE_NAME, credentials);
        return connection;
    }*/

    public void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                 Class.forName(DRIVER);
                driverIsLoaded = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




}
