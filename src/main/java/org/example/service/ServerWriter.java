package org.example.service;

import org.example.model.MyClient;

import java.io.*;
import java.net.Socket;

public class ServerWriter extends Thread {

    private final BufferedReader userInputReader;
    private final BufferedWriter sender;
    private final FileSender fileSender;

    public ServerWriter(BufferedReader userInputReader, BufferedWriter sender, FileSender fileSender) {
        this.userInputReader = userInputReader;
        this.sender = sender;
        this.fileSender = fileSender;
    }

    @Override
    public void run() {

        while (true) {
            String userInput;
            try {
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
