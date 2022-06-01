package com.example.vfarmrdbackend.services;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtService {
    public static int getUser_idFromToken(String jwtToken) {
        String[] chunks = jwtToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        payload = payload.substring(1, payload.length() - 1);
        String[] keyValuePairs = payload.split(",");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].replace("\"", "").trim(), entry[1].replace("\"", "").trim());
        }
        return Integer.parseInt(map.get("jti"));
    }
}
