package org.example.securityapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RespFilterUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String fail(Integer status, String msg) {
        Resp<?> resp = new Resp(status, msg);
        try {
            return mapper.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json 변환 실패");
        }
    }
}