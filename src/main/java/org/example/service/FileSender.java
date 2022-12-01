package org.example.service;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileSender extends Thread {

    private final Socket socket;
    private String path;

    public FileSender(Socket socket) {
        this.socket = socket;
    }

    public void sendFile(String path){
        this.path = path;
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1024];
        File file = new File(path);

        try {
            OutputStream fileSender = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            InputStream inputStream = Files.newInputStream(file.toPath());
            int n = inputStream.read(bytes);
            while (n != -1){
                fileSender.write(bytes);
                fileSender.flush();
                n = inputStream.read(bytes);
            }
            inputStream.close();
            fileSender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
