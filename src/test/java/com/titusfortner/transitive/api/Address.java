package com.titusfortner.transitive.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Address extends BaseAPI {
    String BASE_URL = System.getProperty("BASE_URL", "http://localhost:3000");

    public Map<String, String> createAddress(Map<String, String> address, String rememberToken) {
        String cookieToken = "remember_token=" + rememberToken;

        Map<String, Object> addressValues = new HashMap<>();
        addressValues.put("address", convertToApiFormat(address));

        HttpResponse<String> response = post(addressValues, BASE_URL + "/addresses", cookieToken);
        try {
            return new ObjectMapper().readValue(response.body(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getAddress(String id, String rememberToken) {
        String cookieToken = "remember_token=" + rememberToken;
        HttpResponse<String> response = get(BASE_URL + "/addresses/" + id, cookieToken);
        if (response.statusCode() == 404) {
            return null;
        }
        try {
            Map<String, Object> result = new ObjectMapper().readValue(response.body(), HashMap.class);
            return convertFromApi(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> convertFromApi(Map<String, Object> result) {
        Map<String, String> resultMap = new HashMap<>();
        for (String name : result.keySet())
            if (Objects.equals(name, "address1")) {
                resultMap.put("streetAddress", (String) result.get(name));
            } else if (Objects.equals(name, "address2")) {
                resultMap.put("secondaryAddress", (String) result.get(name));
            } else if (Objects.equals(name, "age")) {
                resultMap.put(name, String.valueOf(result.get(name)));
            } else if (!(result.get(name) instanceof String)) {
                // ignore
            } else {
                resultMap.put(snakeToCamel(name), (String) result.get(name));
            }
        return resultMap;
    }

    private Map<String, Object> convertToApiFormat(Map<String, String> address) {
        Map<String, Object> addressMap = new HashMap<>();
        for (String name : address.keySet())
            if (Objects.equals(name, "streetAddress")) {
                addressMap.put("address1", address.get(name));
            } else if (Objects.equals(name, "secondaryAddress")) {
                addressMap.put("address2", address.get(name));
            } else if (Objects.equals(name, "age")) {
                addressMap.put("age", Integer.parseInt(address.get("age")));
            } else {
                addressMap.put(camelToSnake(name), address.get(name));
            }
        return addressMap;
    }

    private static String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    private static String snakeToCamel(String str) {
        return Pattern.compile("_([a-z])")
                .matcher(str)
                .replaceAll(m -> m.group(1).toUpperCase());
    }
}
