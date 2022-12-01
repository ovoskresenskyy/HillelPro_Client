package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ServerWriter extends Thread {

    private final BufferedReader userInputReader;
    private final BufferedWriter sender;

    public ServerWriter(BufferedReader userInputReader, BufferedWriter sender) {
        this.userInputReader = userInputReader;
        this.sender = sender;
    }

    @Override
    public void run() {

        while (true) {
            String userInput;
            try {
                userInput = userInputReader.readLine();
                sender.write(userInput + System.lineSeparator());
                sender.flush();
                if (userInput.equals("-exit")) {
                    Client.closeConnection();
                }
            } catch (IOException e) {
                Client.closeConnection();
            }

        }
    }
}
