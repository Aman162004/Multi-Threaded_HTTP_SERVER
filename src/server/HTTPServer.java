package server;

import utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Core server logic, binds to port and delegates to thread pool.(Concurrency and Main Loop)

public class HTTPServer {
    private final int port;
    private final int threadPoolSize;
    private final String webRoot;
    private boolean isRunning = true;

    public HTTPServer(int port, int threadPoolSize, String webRoot) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.webRoot = webRoot;
    }

    public void start() throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Logger.info("Multi-threaded HTTP Server listening on port " + port);
            Logger.info("Serving files from folder: " + webRoot);

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Logger.info("Accepted connection from " + clientSocket.getInetAddress());

                    threadPool.execute(new ClientHandler(clientSocket, webRoot));
                } catch (IOException e) {
                    if (isRunning) {
                        Logger.error("Failed to accept client connection: " + e.getMessage());
                    }
                }
            }
        } finally {
            Logger.info("Shutting down worker threads...");
            threadPool.shutdown();
        }
    }

    public void stop() {
        this.isRunning = false;
        Logger.info("Initiated server shutdown.");
    }
}
