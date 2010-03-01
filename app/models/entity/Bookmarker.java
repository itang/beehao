package models.entity;

import models.api.Ownerable;
import siena.Id;
import siena.Model;

import java.util.Date;

/**
 * Bookmarker 书签.
 *
 * @author itang
 */
public class Bookmarker extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //关键字
    public String key;
    //拥有者
    public String username;
    //名称
    public String name;
    //URL
    public String url;
    //排序
    public Integer sort = 0;
    //描述
    public String description = "";
    //创建时间
    public Date createAt = new Date();
    //点击量
    public int hit = 0;
    //标签
    public String tag = "default";

    public Bookmarker(String user) {
        this.username = user;
    }

    public String toString() {
        return name + "(" + id + ", " + key + ")";
    }

    public String owner() {
        return username;
    }
}
