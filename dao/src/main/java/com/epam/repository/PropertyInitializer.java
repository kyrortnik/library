package com.epam.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.epam.repository.utils.DBConstants.*;

public class PropertyInitializer {

    private Properties properties;

    public PropertyInitializer() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = PropertyInitializer.class.getClassLoader().getResourceAsStream(DATABASE_CONFIG_PATH)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
