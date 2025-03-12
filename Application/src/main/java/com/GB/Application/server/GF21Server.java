package com.GB.Application.server;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GF21Server {

    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private boolean running = true;

    @PostConstruct
    public void start() {
        int port = 5000; // Port to listen on
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("GF-21 Server started on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New GF-21 device connected: " + clientSocket.getInetAddress());
                executorService.submit(new GF21ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Error starting GF-21 server: " + e.getMessage());
        }
    }

    @PreDestroy
    public void stop() {
        running = false;
        executorService.shutdown();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping GF-21 server: " + e.getMessage());
        }
    }
}