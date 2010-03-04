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
    public String username;
    //作者(昵称)
    public String author;
    //标题
    public String title;
    //内容
    public Blob content;
    //创建时间
    public Date createAt;
    //最新修改修改时间
    public Date lastModifiedAt;
    //状态
    public int status;
    //点击率
    public Integer hit;
    //留言量
    public Integer comments;

    public Blog(String user) {
        this.username = user;
    }

    public Blog(String user, String title, String content) {
        this.username = user;
        this.title = title;
        this.content(content);
        this.createAt = new Date();
        this.status = STATUS_PUBLISHED;
    }

    public void content(String content) {
        this.content = new Blob(content.getBytes());
    }

    public boolean isPublished() {
        return this.status == STATUS_PUBLISHED;
    }

    public String owner() {
        return this.username;
    }
}
