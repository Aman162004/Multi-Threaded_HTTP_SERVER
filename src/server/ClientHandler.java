package server;


import http.HttpRequest;
import http.HttpResponse;
import http.MimeTypes;
import utils.Logger;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


// Handles incoming client requests in a separate thread. Request Processing & Dispatching.


public class ClientHandler implements Runnable {
   private final Socket clientSocket;
   private final String webRoot;


   public ClientHandler(Socket socket, String webRoot) {
       this.clientSocket = socket;
       this.webRoot = webRoot;
   }


   @Override
   public void run() {
       try (
           BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream())
       ) {
           HttpRequest request;
           try {
               request = new HttpRequest(in);
           } catch (IOException e) {
               Logger.warn("Failed to parse request: " + e.getMessage());
               HttpResponse.sendErrorResponse(out, 400, "Bad Request");
               return;
           }


           Logger.info(Thread.currentThread().getName() + " processing: " + request.getMethod() + " " + request.getPath());


           String method = request.getMethod();
           String path = request.getPath();


           if (method.equals("GET")) {
               handleGet(out, path);
           } else if (method.equals("HEAD")) {
               handleHead(out, path);
           } else {
               Logger.warn("Unsupported method: " + method);
               HttpResponse.sendErrorResponse(out, 501, "Not Implemented");
           }


       } catch (IOException e) {
           Logger.error("Client handler exception: " + e.getMessage());
       } finally {
           closeSocket();
       }
   }


   private void handleGet(BufferedOutputStream out, String path) {
       File file = new File(webRoot, path);


       if (file.exists() && !file.isDirectory()) {
           Logger.info("Serving file: " + file.getPath());
           String contentType = MimeTypes.getContentType(path);
           HttpResponse.sendFileResponse(out, file, contentType);
       } else {
           Logger.warn("File not found: " + file.getPath());
           HttpResponse.sendErrorResponse(out, 404, "Not Found");
       }
   }


   private void handleHead(BufferedOutputStream out, String path) {
       // Just acknowledging HEAD method exists as a simple extension
       File file = new File(webRoot, path);
       if (file.exists() && !file.isDirectory()) {
           Logger.info("HEAD request for: " + file.getPath());
           // Normally HEAD just sends headers without body. We'll simplify.
           HttpResponse.sendErrorResponse(out, 200, "OK (HEAD)");
       } else {
           HttpResponse.sendErrorResponse(out, 404, "Not Found");
       }
   }


   private void closeSocket() {
       try {
           if (clientSocket != null && !clientSocket.isClosed()) {
               clientSocket.close();
           }
       } catch (IOException e) {
           Logger.error("Error closing socket: " + e.getMessage());
       }
   }
}
