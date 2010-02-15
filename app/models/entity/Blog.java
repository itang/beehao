package models.entity;

import com.google.appengine.api.datastore.Blob;
import models.api.Ownerable;
import siena.Id;
import siena.Model;

import java.util.Date;

/**
 * Blog.
 *
 * @author itang
 */
public class Blog extends Model implements Ownerable<String> {
    public static final int STATUS_PUBLISHED = 1;
    public static final int STATUS_UNPUBLISHED = 0;
    public static final int STATUS_DELETED = -1;
    @Id
    public Long id;
    //用户
    public String user;
    //作者(昵称)
    public String author;
    //标题
    public String title;
    //内容
    public Blob content;
    //创建时间
    public Date createAt;
    public int status;
    public Integer hit;
    public Integer comments;


    public Blog(String user) {
        this.user = user;
    }

    public Blog(String user, String title, Blob content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createAt = new Date();
        this.status = STATUS_PUBLISHED;
    }

    public String owner() {
        return this.user;
    }
}
