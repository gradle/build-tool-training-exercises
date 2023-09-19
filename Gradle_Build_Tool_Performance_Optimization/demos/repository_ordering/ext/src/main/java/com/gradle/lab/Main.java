package com.gradle.lab;

public class Main {
    public String getMessage() {
        return "Hi there ext!";
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.getMessage());
    }
}