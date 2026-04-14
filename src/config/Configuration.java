package config;

import utils.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Configuration manager to load properties from a file.(Configuration and Setup)

public class Configuration {
    private static Configuration instance;
    private Properties properties;

    private int port = 8080;
    private int threadPoolSize = 10;
    private String webRoot = "www";

    private Configuration(String filename) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
            this.port = Integer.parseInt(properties.getProperty("server.port", "8080"));
            this.threadPoolSize = Integer.parseInt(properties.getProperty("server.threadPoolSize", "10"));
            this.webRoot = properties.getProperty("server.webRoot", "www");
            Logger.info("Configuration loaded successfully from " + filename);
        } catch (IOException | NumberFormatException e) {
            Logger.error("Could not load configuration from " + filename + ", using defaults.");
        }
    }

    public static Configuration getInstance(String filename) {
        if (instance == null) {
            instance = new Configuration(filename);
        }
        return instance;
    }

    public int getPort() { return port; }
    public int getThreadPoolSize() { return threadPoolSize; }
    public String getWebRoot() { return webRoot; }
}
