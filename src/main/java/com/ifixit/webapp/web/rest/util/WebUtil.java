package com.ifixit.webapp.web.rest.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class WebUtil {

    public static JSONObject objectToJSONObject(Object object) {
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    public static JSONObject convertsToJSONObject(Object object) {
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    public static JSONArray objectToJSONArray(Object object) {
        Object json = null;
        JSONArray jsonArray = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONArray) {
            jsonArray = (JSONArray) json;
        }
        return jsonArray;
    }

    public static String removeAscii(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        input = Normalizer.normalize(input, Normalizer.Form.NFD);
        return input.replaceAll("[^\\x00-\\x7F]", "");
    }

    public static String toPrettyURL(String string) {
        return Normalizer.normalize(string.toLowerCase(), Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\p{Alnum}]+", "-");
    }

//    public JSONArray getJsonObjectOrJsonArray(Object object) {
//        JSONArray jsonArray = new JSONArray();
//        if (object instanceof Map) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put((Map) object);
//            jsonArray.add(jsonObject);
//        } else if (object instanceof List) {
//            jsonArray.addAll((List) object);
//        }
//        return jsonArray;
//    }
}
