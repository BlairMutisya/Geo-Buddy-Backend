package com.GB.Application.server;

import com.GB.Application.model.GF21Message;
import com.GB.Application.service.GF21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Component
public class GF21ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    @Autowired
    private GF21Service gf21Service;

    public GF21ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from GF-21: " + inputLine);

                // Process the message
                GF21Message message = GF21Message.parse(inputLine);
                gf21Service.processMessage(message);

                // Send a response back to the GF-21 device
                out.println("ACK");
            }
        } catch (IOException e) {
            System.err.println("Error handling GF-21 client: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing GF-21 client connection: " + e.getMessage());
            }
        }
    }
}