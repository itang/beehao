package myutils;

import java.util.Map;

public interface Result {
    boolean getSuccess();

    String getMessage();

    String getDescription();

    Object getData();
}
