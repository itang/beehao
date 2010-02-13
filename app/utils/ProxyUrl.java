package utils;

import java.util.*;

/**
 * 代理URL.
 *
 * @author itang
 */
public class ProxyUrl {
    static Map<String, String> urls = new HashMap<String, String>();

    static {
        urls.put("http://googlegroups", "http://groups.google.com");
    }

    public static String real(String url) {
        for (Map.Entry<String, String> entry : urls.entrySet()) {
            String f = entry.getKey();
            if (url.indexOf(f) == 0)
                return entry.getValue() + url.substring(f.length());
        }

        return url;
    }
}
