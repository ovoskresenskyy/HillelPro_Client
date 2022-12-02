package org.example.service;

import org.example.model.MyClient;

import java.io.*;
import java.net.Socket;

public class ServerWriter extends Thread {

    private final Socket socket;

    public ServerWriter(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {

        FileSender fileSender = new FileSender(socket);

        while (true) {
            String userInput;
            try (BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))) {
                userInput = userInputReader.readLine();
                sender.write(userInput + System.lineSeparator());
                sender.flush();
                if (userInput.equals("-exit")) MyClient.getInstance().closeConnection();

                String[] words = userInput.split(" ");
                if (words.length > 1 && words[0].equals("-file")) fileSender.sendFile(words[1]);
            } catch (IOException e) {
                MyClient.getInstance().closeConnection();
            }

        }
    }
}
