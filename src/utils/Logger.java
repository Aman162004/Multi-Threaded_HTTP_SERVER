package utils;


import java.text.SimpleDateFormat;
import java.util.Date;


// Simple logger utility for console output. Basic logging structure.


public class Logger {
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


   public static void info(String message) {
       log("INFO", message);
   }


   public static void warn(String message) {
       log("WARN", message);
   }


   public static void error(String message) {
       log("ERROR", message);
   }


   private static void log(String level, String message) {
       String time = dateFormat.format(new Date());
       System.out.println(String.format("[%s] [%s] %s", time, level, message));
   }
}
