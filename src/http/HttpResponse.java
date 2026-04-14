package http;


import utils.Logger;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;


// Handles building and sending an HTTP response.(Response Formatting & File delivery)


public class HttpResponse {
   public static void sendFileResponse(BufferedOutputStream out, File file, String contentType) {
       try {
           byte[] fileData = readFileData(file);
           sendResponseHeader(out, 200, "OK", contentType, fileData.length);
           out.write(fileData);
           out.flush();
       } catch (IOException e) {
           Logger.error("Error reading file: " + file.getPath());
           sendErrorResponse(out, 500, "Internal Server Error");
       }
   }


   public static void sendErrorResponse(BufferedOutputStream out, int statusCode, String statusText) {
       try {
           String errorMessage = "<html><head><title>" + statusCode + " " + statusText + "</title></head>" +
                                  "<body><h1>" + statusCode + " " + statusText + "</h1>" +
                                  "<p>Sorry, the server encountered an error processing your request.</p>" +
                                  "</body></html>";
           sendResponseHeader(out, statusCode, statusText, "text/html", errorMessage.length());
           out.write(errorMessage.getBytes());
           out.flush();
       } catch (IOException e) {
           Logger.error("Failed to send error response.");
       }
   }


   private static void sendResponseHeader(BufferedOutputStream out, int statusCode, String statusText, String contentType, int contentLength) throws IOException {
       PrintWriter writer = new PrintWriter(out, true);
       writer.print("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
       writer.print("Server: AdvancedJavaWebServer/1.0\r\n");
       writer.print("Content-Type: " + contentType + "\r\n");
       writer.print("Content-Length: " + contentLength + "\r\n");
       writer.print("Connection: close\r\n");
       writer.print("\r\n"); // Blank line marking end of headers
       writer.flush();
   }
   private static byte[] readFileData(File file) throws IOException {
       try (FileInputStream fileIn = new FileInputStream(file)) {
           byte[] fileData = new byte[(int) file.length()];
           int bytesRead = fileIn.read(fileData);
           if (bytesRead != file.length()) {
               Logger.warn("Possible partial read for file " + file.getName());
           }
           return fileData;
       }
   }
}
