package org.rdutta.notificationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJSONString {
    public static String objectToJSONString(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return new ObjectMapper().writeValueAsString(object);
    }
}
