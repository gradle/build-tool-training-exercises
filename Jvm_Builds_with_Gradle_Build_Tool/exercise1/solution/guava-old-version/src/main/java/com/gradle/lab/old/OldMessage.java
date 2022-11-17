package com.gradle.lab.old;

import com.google.common.base.CharMatcher;

public class OldMessage {

    private String text;

    public OldMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean hasWhitespace() {
        return CharMatcher.WHITESPACE.matchesAnyOf(text);
    }
}
