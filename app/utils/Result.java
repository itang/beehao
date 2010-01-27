package utils;

public interface Result {
    boolean getSuccess();

    String getMessage();

    String getDescription();

    Object getData();
}
