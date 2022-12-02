package org.example.service;

import java.io.*;
import java.net.Socket;

public class FileSender extends Thread {

    private final Socket socket;
    private File file;

    public FileSender(Socket socket) {
        this.socket = socket;
    }

    public void sendFile(String path) {

        File file = new File(path);
        if (file.exists() && !file.isDirectory()) this.start();
        else System.out.println("Can't send file. It doesn't exist!");
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        try (OutputStream fileSender = socket.getOutputStream();
             InputStream fileReader = new FileInputStream(file)) {

            for (int count = -1; (count = fileReader.read(buffer)) != -1; ) {
                fileSender.write(buffer, 0, count);
            }
            fileSender.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
