package repository;


public class DataBaseConfig {

    public DataBaseConfig() {
        loadJdbcDriver();

    }

    private static final String DRIVER = "database.driver";


    private boolean driverIsLoaded = false;


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
