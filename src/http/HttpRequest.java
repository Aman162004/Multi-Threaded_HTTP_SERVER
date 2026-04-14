package http;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// Parses and stores an incoming HTTP request.(Request Parsing & Validation)


public class HttpRequest {
   private String method;
   private String path;
   private String version;
   private Map<String, String> headers = new HashMap<>();


   public HttpRequest(BufferedReader in) throws IOException {
       String requestLine = in.readLine();
       if (requestLine == null || requestLine.isEmpty()) {
           throw new IOException("Empty request line");
       }


       String[] parts = requestLine.split(" ");
       if (parts.length != 3) {
           throw new IOException("Invalid request line format");
       }


       this.method = parts[0];
       this.path = parts[1];
       this.version = parts[2];


       if (this.path.equals("/")) {
           this.path = "/index.html"; // Default file
       }


       // Parse headers
       String headerLine;
       while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
           String[] headerParts = headerLine.split(":", 2);
           if (headerParts.length == 2) {
               headers.put(headerParts[0].trim(), headerParts[1].trim());
           }
       }
   }


   public String getMethod() { return method; }
   public String getPath() { return path; }
   public String getVersion() { return version; }
   public String getHeader(String name) { return headers.get(name); }
}
