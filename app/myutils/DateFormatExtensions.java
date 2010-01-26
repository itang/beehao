package myutils;

import play.templates.JavaExtensions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormatExtensions extends JavaExtensions {
    public static String prettyFormat(final Date date, final String pattern) {
        if (date == null) return "";
        try {
            long that = date.getTime();
            long now = new Date().getTime();
            int ss = (int) (now - that) / 1000;
            if (ss < 60) {     //
                return "刚刚";
            } else if (ss < 3600) {
                return ss / 60 + "分钟以前";
            } else if (ss < 3600 * 24) {
                return ss / 3600 + "小时以前";
            } else {
                String applyPattern = pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern;
                System.out.println(new SimpleDateFormat(applyPattern).format(date));
                return new SimpleDateFormat(applyPattern).format(date);
            }
        } catch (Exception e) {
            return "";
        }
    }
}
