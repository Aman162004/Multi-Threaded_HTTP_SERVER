package server;


import config.Configuration;
import utils.Logger;


import java.io.IOException;


/**
* Main application entry point for the Web Server.
* Authored by: Student 1 (Architecture & Setup)
*/
public class Main {
   public static void main(String[] args) {
       Logger.info("Starting Group 5 Web Server...");
      
       try {
           Configuration config = Configuration.getInstance("config.properties");
           HTTPServer server = new HTTPServer(config.getPort(), config.getThreadPoolSize(), config.getWebRoot());
           server.start();
       } catch (IOException e) {
           Logger.error("Failed to start server: " + e.getMessage());
           System.exit(1);
       }
   }
}
