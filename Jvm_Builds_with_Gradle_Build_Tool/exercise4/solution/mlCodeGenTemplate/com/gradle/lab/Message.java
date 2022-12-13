package com.gradle.lab;

import com.google.common.base.CharMatcher;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.joda.time.LocalTime;

import java.nio.charset.Charset;

public class Message {

    private String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getTextWithTime() {
        LocalTime localTime = LocalTime.now();

        return text + " The time is currently: " + localTime.toString();
    }

    public boolean hasWhitespace() {
        return CharMatcher.whitespace().matchesAnyOf(text);
    }

    public HashCode getTextHash() {
        return Hashing.fingerprint2011().hashString(text, Charset.defaultCharset());
    }
}
