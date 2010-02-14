package models.entity;

import models.api.Ownerable;
import org.apache.commons.lang.StringUtils;
import siena.Filter;
import siena.Id;
import siena.Model;
import siena.Query;

import java.util.Date;
import java.util.List;

/**
 * Album 相册.
 *
 * @author itang
 */
public class Album extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //名称
    public String name;
    //备注
    public String notes;
    //封面照片
    public Photo cover;
    //拥有者
    public final String user;
    //照片
    @Filter("album")
    public Query<Photo> photos;
    //创建时间
    public Date createAt = new Date();

    public Album(String user) {
        this.user = user;
    }

    public Album(String user, String name, String notes) {
        this.user = user;
        this.name = name;
        this.notes = notes;
    }

    public String owner() {
        return this.user;
    }

    public String toString() {
        return name;
    }

    public List<Photo> photos() {
        return photos("-createAt");
    }

    public List<Photo> photos(String order) {
        return StringUtils.isBlank(order) ?
                photos.fetch()
                : photos.order(order).fetch();
    }
}
