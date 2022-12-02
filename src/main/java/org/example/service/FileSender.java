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
