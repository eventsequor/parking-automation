package com.parking.utils;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum PropertiesUtils {

    PROPERTIES_UTILS;

    private final Properties globalProperties = new Properties();

    private static final String GLOBAL_PROPERTIES_FILE_NAME = "global.properties";
    private static final String EXPLICITLY_WAIT = "wait.explicitly";
    private static final String IMPLICITLY_WAIT = "wait.implicitly";
    private static final String BROWSER = "browser";


    private String getGlobalProperty(String propertyName) {
        if (globalProperties.isEmpty()) {
            loadProperties(globalProperties);
        }
        return globalProperties.getProperty(propertyName);
    }

    private void loadProperties(Properties properties) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(GLOBAL_PROPERTIES_FILE_NAME);
            properties.load(inputStream);
            if (inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getExplicitlyWait() {
        String property = getGlobalProperty(EXPLICITLY_WAIT);
        return property != null ? Integer.parseInt(property.trim()) : 0;
    }

    public int getImplicitlyWait() {
        String property = getGlobalProperty(IMPLICITLY_WAIT);
        return property != null ? Integer.parseInt(property.trim()) : 0;
    }

    public String getBrowser() {
        String browser = getGlobalProperty(BROWSER);
        return browser != null ? browser : "undefined";
    }

}
