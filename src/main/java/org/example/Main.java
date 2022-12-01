package org.example;

import org.example.model.MyClient;

public class Main {
    public static void main(String[] args) {

        MyClient myClient = MyClient.getInstance();
        myClient.start();

    }
}
