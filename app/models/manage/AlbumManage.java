package models.manage;

import models.api.Ownerable;
import models.api.OwnerableManage;
import models.entity.Album;

/**
 * AlbumManage.
 *
 * @author itang
 */
public class AlbumManage extends OwnerableManage<Album> implements Ownerable<String> {
    public final String owner;
    public final static AlbumManage instance = new AlbumManage(null);

    public static AlbumManage instance(String owner) {
        return new AlbumManage(owner);
    }

    public AlbumManage(String owner) {
        this.owner = owner;
    }

    public String owner() {
        return owner;
    }

    public void add(String name, String notes) {
        new Album(this.owner, name, notes).insert();
    }
}
