package models.manage;

import models.api.OwnerableManage;
import models.entity.Bookmarker;

import java.util.List;

/**
 * BookmarkerManage.
 *
 * @author itang
 */
public class BookmarkerManage extends OwnerableManage<Bookmarker> {
    public final String owner;
    static BookmarkerManage instance = new BookmarkerManage(null);

    public BookmarkerManage(String owner) {
        this.owner = owner;
    }

    public Bookmarker getByName(String name) {
        return query().filter("name", name).get();
    }

    public List<Bookmarker> getAll() {
        return query().order("-hit").fetch();
    }

    public Bookmarker increaseOneHit(Bookmarker b) {
        b.hit = b.hit + 1;
        b.update();

        return b;
    }

    public BookmarkerManage add(String key, String name, String url) {
        Bookmarker b = new Bookmarker(this.owner);
        b.key = key;
        b.name = name;
        b.url = url;
        b.insert();

        return this;
    }

    public String owner() {
        return this.owner;
    }

    public static BookmarkerManage instance(String owner) {
        return new BookmarkerManage(owner);
    }

    public static BookmarkerManage instance() {
        return instance;
    }

}
