package com.biztech.demo.util;

import com.google.gson.Gson;

public class StringUtil {

    public static boolean isEmpty(String a) {
        return (a==null||a.length()==0)?true:false;
    }
    public static Object copy(Object source, Object target) throws Exception {
        if (source == null) {
            return null;
        } else {
            Gson gson = new Gson();
            String sourceStr = gson.toJson(source);
            return gson.fromJson(sourceStr, target.getClass());
        }

    }
}
