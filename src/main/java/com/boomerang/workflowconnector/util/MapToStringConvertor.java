package com.boomerang.workflowconnector.util;

import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kanhaiya on 26/09/16.
 */
public class MapToStringConvertor {

    public static String convert(HashMap<String, Object> hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(hashMap);
        return jsonObject.toString();
    }

}
