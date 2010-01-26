package models;

import siena.Query;
import siena.Model;

import java.util.List;

public class SayViewer extends AbstractViewer<Say> {
    public final String user;

    public SayViewer(String user) {
        this.user = user;
    }

    public List<Say> getAll() {
        return all().order("-createAt").fetch();
    }

    public Say add(String content) {
        Say say = new Say(user(), content);
        say.insert();

        return say;
    }

    public SayViewer reply(String content, Say target) {
        Say say = new Say(user(), content, target);
        say.insert();

        target.replys += 1;
        target.update();

        return this;
    }

    public Say get(Long id) {
        return all().filter("id", id).get();
    }

    @Override
    protected Class<Say> clazz() {
        return Say.class;
    }

    @Override
    protected String user() {
        return this.user;
    }


}
