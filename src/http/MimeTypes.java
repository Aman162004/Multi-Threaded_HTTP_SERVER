package http;
import java.util.HashMap;
import java.util.Map;


// Utility for mapping file extensions to MIME types.


public class MimeTypes {
   private static final Map<String, String> mimeTypes = new HashMap<>();


   static {
       mimeTypes.put("htm", "text/html");
       mimeTypes.put("html", "text/html");
       mimeTypes.put("css", "text/css");
       mimeTypes.put("js", "application/javascript");
       mimeTypes.put("json", "application/json");
       mimeTypes.put("png", "image/png");
       mimeTypes.put("jpg", "image/jpeg");
       mimeTypes.put("jpeg", "image/jpeg");
       mimeTypes.put("gif", "image/gif");
       mimeTypes.put("ico", "image/vnd.microsoft.icon");
       mimeTypes.put("txt", "text/plain");
       mimeTypes.put("pdf", "application/pdf");
   }


   public static String getContentType(String path) {
       int dotIndex = path.lastIndexOf('.');
       if (dotIndex >= 0 && dotIndex < path.length() - 1) {
           String extension = path.substring(dotIndex + 1).toLowerCase();
           return mimeTypes.getOrDefault(extension, "application/octet-stream");
       }
       return "text/plain"; // Default for unknown/no extensions
   }
}
