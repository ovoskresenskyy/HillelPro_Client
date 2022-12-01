package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerReader extends Thread {

    BufferedReader listener;

    public ServerReader(BufferedReader listener) {
        this.listener = listener;
    }

    @Override
    public void run() {

        String msg = "";
        while (true) {
            try {
                if ((msg = listener.readLine()) == null) break;
                System.out.println(msg);
            } catch (IOException e) {
                Client.closeConnection();
            }
        }
    }
}
