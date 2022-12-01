package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 10160);
            BufferedReader listener = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            ServerReader serverReader = new ServerReader(listener);
            serverReader.start();

            ServerWriter serverWriter = new ServerWriter(userInputReader, sender);
            serverWriter.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to connect to server. Try again late");
        }
    }

    public static void closeConnection(){
        System.out.println("You was disconnected from the server. Come back later.");
        System.exit(0);
    }
}
