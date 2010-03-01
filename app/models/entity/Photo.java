package models.entity;

import com.google.appengine.api.datastore.Blob;
import models.api.Ownerable;
import siena.Id;
import siena.Index;
import siena.Model;
import siena.Query;

import java.util.Date;
import java.util.List;

/**
 * Photo 照片.
 *
 * @author itang
 */
public class Photo extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //名称
    public String name;
    //内容
    public Blob content;
    //创建时间
    public Date createAt;
    //备注
    public String notes;

    //所属相册
    @Index("album_index")
    public Album album;

    public Photo(Album album) {
        this.album = album;
        this.createAt = new Date();
    }

    public Photo(Album album, String name, Blob content, String notes) {
        this.album = album;
        this.name = name;
        this.content = content;
        this.notes = notes;
        this.createAt = new Date();
    }

    static Query<Photo> all() {
        return Model.all(Photo.class);
    }

    public static List<Photo> getAll() {
        return all().order("-createAt").fetch();
    }

    public static Photo add(Album album, String name, Blob normal, String notes) {
        Photo photo = new Photo(album, name, normal, notes);
        photo.insert();
        return photo;
    }

    public static Photo get(Long id) {
        return all().filter("id", id).get();
    }

    public String owner() {
        album.get();
        return album.owner();
    }
}
