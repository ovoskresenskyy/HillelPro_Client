package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    private static DataInputStream serverListener;

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("localhost", 10160)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

//            serverListener = new DataInputStream(clientSocket.getInputStream());
            BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

//            listenServer();

//            if (input.ready()) {
                String clientCommand = input.readLine();

                sender.write(clientCommand);
                sender.flush();

//                listenServer();
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private static void listenServer() throws IOException {
//        if (serverListener.read() > -1) {
//            String in = serverListener.readUTF();
//            System.out.println(in);
//        }
//    }
}
