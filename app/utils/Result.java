package utils;

/**
 * 操作结果.
 *
 * @author itang
 */
public interface Result {
    boolean getSuccess();

    String getMessage();

    String getDescription();

    Object getData();
}
