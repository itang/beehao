package models;

import siena.*;

import java.util.Date;
import java.util.List;

public class Say extends Model implements Ownerable<String> {
    @Id
    public Long id;

    public String user;
    public String nickname;

    @Max(200)
    @NotNull
    public String content;

    public Date createAt = new Date();
    public String createTools = "tangqiong.com";//发布工具

    public Say target;
    public Integer replys = 0;
    public String replyTo;//回复给谁(对谁说 )


    public Say(String user, String content) {
        this(user, content, null);
    }

    public Say(String user, String content, Say target) {
        this.user = user;
        this.content = content;
        this.target = target;
    }

    public static Say get(Long id) {
        return all().filter("id", id).get();
    }

    public static List<Say> getAll() {
        return all().order("-createAt").fetch();
    }

    public static SayViewer viewer(String user) {
        return new SayViewer(user);
    }

    private static Query<Say> all() {
        return Model.all(Say.class);
    }

    public String owner() {
        return user;
    }
}
