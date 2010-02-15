package utils;

import com.google.appengine.api.datastore.Blob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MyJavaExtensions extends play.templates.JavaExtensions {
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

    public static String format(final Date date, final String pattern) {
        if (date == null) return "";
        return new SimpleDateFormat(pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern)
                .format(date);
    }

    public static String rssDateFormat(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        return sdf.format(date);
    }

    public static String asString(Blob blob) {
        if (blob == null) return "";
        return new String(blob.getBytes());
    }
}
