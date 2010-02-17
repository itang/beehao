package utils;

/**
 * StringUtil.
 *
 * @author itang
 */
public class StringUtil {
    public static String nvl(String str1, String str2) {
        return str1 == null ? str2 : str1;
    }
}
