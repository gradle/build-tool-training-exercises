package com.gradle.demo;

public class MessageBuilder {

    private static final String HELLO_TXT = "Hi there!";
    private static final String BYE_TXT = "See you next time!";

    public static Message helloMessage() {
        return new Message(HELLO_TXT);
    }

    public static Message byeMessage() {
        return new Message(BYE_TXT);
    }
}
