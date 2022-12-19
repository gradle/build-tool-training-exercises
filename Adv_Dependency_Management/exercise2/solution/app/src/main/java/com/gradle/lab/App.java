package com.gradle.lab;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Message message = new Message("Hi!");

        System.out.println(new App().getGreeting());
        System.out.println(message.getText());
    }
}
