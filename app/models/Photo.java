package models;

import com.google.appengine.api.datastore.Blob;
import siena.Id;
import siena.Model;
import siena.Query;

import java.util.Date;
import java.util.List;


public class Photo extends Model {
    @Id
    public Long id;
    public String name;
    public Blob content;
    public Date createAt;

    public Photo() {
        this.createAt = new Date();
    }

    public Photo(String name, Blob content) {
        this.name = name;
        this.content = content;
        this.createAt = new Date();
    }


    static Query<Photo> all() {
        return Model.all(Photo.class);
    }

    public static List<Photo> getAll() {
        return all().order("-createAt").fetch();
    }


    public static Photo add(String name, Blob normal) {
        Photo photo = new Photo(name, normal);
        photo.insert();
        return photo;
    }

    public static Photo get(Long id) {
        return all().filter("id", id).get();
    }
}
