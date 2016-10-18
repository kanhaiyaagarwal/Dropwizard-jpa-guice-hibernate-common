package com.boomerang.workflowconnector.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by kanhaiya on 28/09/16.
 */
public class StringToHashMapConvertor {
    public static final Converter<String, HashMap<String, Object>> toHashMap = new AbstractConverter< String, HashMap<String, Object> >() {
        @Override
        public  HashMap<String, Object> convert(String str) {
            if(str == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> map = new HashMap<String, Object>();
            try {
                map = objectMapper.readValue(str, objectMapper.getTypeFactory().
                        constructMapType(HashMap.class, String.class, Object.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }
    };

    public static HashMap<String, Object> convert(String str) {
        if(str == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            map = objectMapper.readValue(str, objectMapper.getTypeFactory().
                    constructMapType(HashMap.class, String.class, Object.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
