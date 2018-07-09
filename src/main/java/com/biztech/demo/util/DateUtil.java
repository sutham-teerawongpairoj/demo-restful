package com.biztech.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String DEFAULT_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static String DefaultDateToString(Date date) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT, DEFAULT_LOCALE);
        return df.format(date);
    }

    public static Date DefaultStringToDate(String str) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT, DEFAULT_LOCALE);
        return df.parse(str);
    }

    public static Date StringToDate(String str, String format, Locale locale) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        return df.parse(str);
    }

    public static String DateToString(Date date, String format, Locale locale) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        return df.format(date);
    }
}
