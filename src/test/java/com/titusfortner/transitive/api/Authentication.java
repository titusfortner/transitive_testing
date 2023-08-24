package com.titusfortner.transitive.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authentication extends BaseAPI {
    String BASE_URL = System.getProperty("BASE_URL", "http://localhost:3000");
    public String rememberToken;

    public void createUser(String email, String password) {
        Map<String, String> userValues = new HashMap<>();
        userValues.put("email", email);
        userValues.put("password", password);
        Map<String, Object> authValues = new HashMap<>();
        authValues.put("user", userValues);

        HttpResponse<String> response = post(authValues, BASE_URL + "/users", "");
        List<String> cookie = response.headers().map().get("set-cookie");
        rememberToken = cookie.get(0).split(";")[0].split("=")[1];
    }

    public Map<String, Object> getUser(String rememberToken) {
        String cookieToken = "remember_token=" + rememberToken;
        HttpResponse<String> response = get(BASE_URL + "/current_user", cookieToken);
        try {
            return new ObjectMapper().readValue(response.body(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
