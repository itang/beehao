package models;

import siena.Id;
import siena.Model;
import siena.Query;

import java.util.Date;

public class Say extends Model {
    @Id
    public Long id;
    public String user;
    public String content;
    public Date createAt = new Date();
    public Say target;
    public Integer replys = 0;

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

    static Query<Say> all() {
        return Model.all(Say.class);
    }
}
