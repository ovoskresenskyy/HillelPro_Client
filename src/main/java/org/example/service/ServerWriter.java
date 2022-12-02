package org.example.service;

import org.example.model.MyClient;
import org.example.model.MyPair;

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
                MyPair parsedInput = parseUserInput(userInput);

                String command = parsedInput.command();
                String parameter = parsedInput.parameter();

                if (command.equals("-exit")) MyClient.getInstance().closeConnection();
                if (command.equals("-file")) fileSender.sendFile(parameter);

            } catch (IOException e) {
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
