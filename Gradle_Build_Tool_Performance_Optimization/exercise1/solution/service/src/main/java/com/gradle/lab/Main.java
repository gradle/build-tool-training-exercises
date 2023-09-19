package com.gradle.lab;

public class Main {
    public String getMessage() {
        return "Hi there service!";
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.getMessage());
    }
}