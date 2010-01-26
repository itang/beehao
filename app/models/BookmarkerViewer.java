package models;

import java.util.*;

public class BookmarkerViewer {
    public final String user;

    public BookmarkerViewer(String user) {
        this.user = user;
    }

    public List<Bookmarker> getAll() {
        return Bookmarker.findByUser(this.user);
    }

    public BookmarkerViewer deleteAll() {
        for (Bookmarker bookmarker : getAll()) {
            bookmarker.delete();
        }

        return this;
    }

    public BookmarkerViewer add(String key, String name, String url) {
        Bookmarker.add(key, name, url, this.user);

        return this;
    }
}
