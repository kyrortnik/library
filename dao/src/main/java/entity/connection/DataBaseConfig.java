package entity.connection;



import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {

    public DataBaseConfig() {
    }
    private static String DRIVER = "org.postgresql.Driver";
    private static String URL = "jdbc:postgresql://localhost/";
    private static String DATABASE_NAME = "myDB";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "admin";
    private boolean driverIsLoaded = false;


    public Connection getConnection() throws SQLException {
        loadJdbcDriver();
        Connection connection;
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        connection = DriverManager.getConnection(URL + DATABASE_NAME, properties);
        return connection;
    }

    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                // Class.forName(DRIVER);
                DriverManager.registerDriver(new Driver());
                driverIsLoaded = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
