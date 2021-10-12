package repository;

import exception.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyInitializer {


    private static final String DATABASE_CONFIG_PATH = "database.properties";
    private Properties properties;



    public PropertyInitializer() {
        loadProperties();
    }

    private void loadProperties() throws DAOException {
        try(InputStream inputStream = PropertyInitializer.class.getClassLoader().getResourceAsStream(DATABASE_CONFIG_PATH)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new DAOException(" Error while loading properties.");
        }
    }

    public Properties getProperties() {
        return properties;
    }
    public String getProperty(String key){
        return properties.getProperty(key);
    }

}
