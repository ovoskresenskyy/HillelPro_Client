package org.example.service;

import java.io.*;
import java.net.Socket;

public class FileSender extends Thread {

    private final OutputStream fileSender;
    private String path;

    public FileSender(Socket socket) {
        try {
            fileSender = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFile(String path) {
        this.path = path;
        start();
    }

    @Override
    public void run() {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            try (InputStream fileReader = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];

                for (int count; (count = fileReader.read(buffer)) != -1; ) {
                    fileSender.write(buffer, 0, count);
                }
                fileSender.flush();
                System.out.println("File successfully sent!");
            } catch (IOException e) {
                System.out.println("File is not available. Can't send data!");
            }

        } else System.out.println("Can't send file. It doesn't exist!");
    }
}
