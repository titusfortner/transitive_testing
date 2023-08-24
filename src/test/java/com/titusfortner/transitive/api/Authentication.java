package com.titusfortner.transitive.api;

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
}
