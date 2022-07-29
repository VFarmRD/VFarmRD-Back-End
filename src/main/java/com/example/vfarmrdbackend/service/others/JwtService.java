package com.example.vfarmrdbackend.service.others;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtService {
    public static Map<String, String> jwtStringToMap(String jwt) {
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        payload = payload.substring(1, payload.length() - 1);
        String[] keyValuePairs = payload.split(",");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].replace("\"", "").trim(), entry[1].replace("\"", "").trim());
        }
        return map;
    }

    public static int getUser_idFromToken(String jwt) {
        Map<String, String> map = jwtStringToMap(jwt);
        return Integer.parseInt(map.get("jti"));
    }

    public static boolean checkJWTIsExpired(String jwt) {
        Map<String, String> map = jwtStringToMap(jwt);
        Timestamp ts = new Timestamp(Long.parseLong(map.get("exp")));
        Date jwtExpDate = new Date(ts.getTime());
        if (jwtExpDate.after(new Date())) {
            return true;
        }
        return false;
    }
}
