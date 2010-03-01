package models.entity;

import siena.*;

/**
 * Config 配置.
 *
 * @author itang
 */
public class Config extends Model {
    @Id
    public Long id;
    //KEY
    public String key;
    //VALUE
    public String value;
    //拥有者
    public String username;

    public Config() {
        //
    }

    public Config(String key, String value, String user) {
        this.key = key;
        this.value = value;
        this.username = user;
    }

    public static Config get(String key, String user) {
        return all().filter("key", key).filter("username", user).get();
    }

    public static void setHomepage(String homepage, String user) {
        Config config = Config.getHomepageConfig(user);
        if (config == null) {
            config = new Config("homepage", homepage, user);
            config.insert();
        } else {
            config.value = homepage;
            config.update();
        }
    }

    public static String getHomepage(String user) {
        Config c = get("homepage", user);
        if (c == null) return null;
        return c.value;
    }

    public static Config getHomepageConfig(String user) {
        return get("homepage", user);
    }

    private static Query<Config> all() {
        return Model.all(Config.class);
    }
}
