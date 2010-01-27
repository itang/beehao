package utils;

import play.templates.JavaExtensions;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatExtensions extends JavaExtensions {
    public static String prettyFormat(final Date date, final String pattern) {
        if (date == null) return "";
        try {
            final long that = date.getTime();
            final long now = new Date().getTime();
            final int ss = (int) (now - that) / 1000;
            if (ss < 60) {     //
                return "刚刚";
            } else if (ss < 3600) {
                return ss / 60 + "分钟前";
            } else if (ss < 3600 * 24) {
                return ss / 3600 + "小时前";
            } else {
                return new SimpleDateFormat(pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern)
                        .format(date);
            }
        } catch (Exception e) {
            return "";
        }
    }
}
