package com.titusfortner.transitive.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class BaseAPI {
    protected static HttpClient client = HttpClient.newHttpClient();

    public static HttpResponse<String> post(Map<String, Object> body, String url, String cookieToken) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .headers("Accept", "application/json", "Content-Type", "application/json", "Cookie", cookieToken)
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(body)))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> get(String url, String cookieToken) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .headers("Accept", "application/json", "Cookie", cookieToken)
                    .timeout(Duration.ofSeconds(10))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
