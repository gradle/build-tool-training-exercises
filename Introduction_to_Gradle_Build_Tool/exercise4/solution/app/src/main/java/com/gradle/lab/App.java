package com.gradle.lab;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public class App {

    AppModel model = new AppModel();

    public String getGreeting() {
        return model.getGreeting();
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        System.out.println(app.getGreeting());

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(app.model.getUrl()));
        String rawResponse = request.execute().parseAsString();
        System.out.println("\n---------\n");
        System.out.println(rawResponse);
    }
}