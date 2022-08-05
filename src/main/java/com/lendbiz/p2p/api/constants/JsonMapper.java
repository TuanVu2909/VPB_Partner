/***************************************************************************
 * Copyright 2018 by VIETIS - All rights reserved.                *    
 **************************************************************************/
package com.lendbiz.p2p.api.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapper.class);

    public static <O> String writeValueAsString(O o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

    public static <T> T convertJsonToObject(String jsonStr, final Class<T> clazz) {
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T convertJsonToObject(String jsonStr, final TypeReference<T> reference) {
        try {
            return mapper.readValue(jsonStr, reference);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
