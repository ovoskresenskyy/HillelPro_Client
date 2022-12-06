package org.example.service;

import org.example.model.MyClient;
import org.example.model.MyPair;

import java.io.*;

public class ServerWriter extends Thread {

    private final BufferedWriter sender;
    private final BufferedReader userInputReader;
    private final FileSender fileSender;

    public ServerWriter(BufferedWriter sender, BufferedReader userInputReader, FileSender fileSender) {
        this.sender = sender;
        this.userInputReader = userInputReader;
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

                MyPair parsedInput = parseUserInput(userInput);
                String command = parsedInput.command();
                String parameter = parsedInput.parameter();

                if (command.equals("-exit")) MyClient.getInstance().closeConnection();
                if (command.equals("-file")) fileSender.sendFile(parameter);
            } catch (IOException e) {
                System.out.println("Trying to send data on the server. Socket is abandoned");
                MyClient.getInstance().closeConnection();
            }
        }
    }

    private MyPair parseUserInput(String userInput) {
        String[] words = userInput.split(" ");

        String command = "";
        String parameter = "";

        for (int i = 0; i < words.length && i < 2; i++) {
            if (i == 0) command = words[i];
            if (i == 1) parameter = words[i];
        }

        return new MyPair(command, parameter);
    }
}
