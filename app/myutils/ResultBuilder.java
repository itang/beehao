package myutils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


final public class ResultBuilder implements Jsonable {
    private final ResultHolder result = new ResultHolder();

    public static ResultBuilder get() {
        return success();
    }

    public static ResultBuilder success() {
        return new ResultBuilder().result(true);
    }

    public static ResultBuilder failure() {
        return new ResultBuilder().result(false);
    }

    public ResultBuilder result(boolean success) {
        result.success = success;
        return this;
    }

    public ResultBuilder msg(String msg) {
        result.message = msg;
        return this;
    }

    public ResultBuilder desc(String desc) {
        result.description = desc;
        return this;
    }

    public ResultBuilder data(Object data) {
        result.data = data;
        return this;
    }

    public ResultBuilder value(String key, Object value) {
        if (result.data == null) {
            result.data = new HashMap<String, Object>();

        }
        ((HashMap) result.data).put(key, value);

        return this;
    }


    public Result build() {
        return result;
    }

    public String toJson() {
        Map<String, Object> o = new HashMap<String, Object>();
        o.put("success", result.success);
        o.put("message", result.message.equals("") ? result.success ? "操作成功!" : "操作失败!" : result.message);
        o.put("data", result.data);
        o.put("description", result.description);
        return new Gson().toJson(o);
    }

    public static class ResultHolder implements Result {
        public boolean success = true;
        public String message = "";
        public String description = "";
        public Object data;

        public ResultHolder() {

        }

        public ResultHolder(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getDescription() {
            return description;
        }

        public Object getData() {
            return data;
        }
    }
}

