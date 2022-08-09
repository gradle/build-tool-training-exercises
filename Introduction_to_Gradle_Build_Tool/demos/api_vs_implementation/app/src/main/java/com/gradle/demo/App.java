package com.gradle.demo;

public class App {

    public static void main(String[] argv) {
        Message helloMessage = MessageBuilder.helloMessage();
        System.out.println(helloMessage);

        System.out.println(MessageBuilder.byeMessage());
    }
}
