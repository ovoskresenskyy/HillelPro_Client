package org.example.model;

import org.example.service.FileSender;
import org.example.service.ServerReader;
import org.example.service.ServerWriter;

import java.io.*;
import java.net.Socket;

public class MyClient {

    private MyClient() {
    }

    private static class ClientHolder {
        private final static MyClient instance = new MyClient();
    }

    public static MyClient getInstance() {
        return MyClient.ClientHolder.instance;
    }


    public void start() {
        try {
            Socket clientSocket = new Socket("localhost", 10160);
            BufferedReader listener = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            FileSender fileSender = new FileSender(clientSocket);

            ServerReader serverReader = new ServerReader(listener);
            serverReader.start();

            ServerWriter serverWriter = new ServerWriter(sender, userInputReader, fileSender);
            serverWriter.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to connect to server. Try again late");
        }
    }

    public void closeConnection() {
        System.out.println("You was disconnected from the server. Come back later.");
        System.exit(0);
    }
}
