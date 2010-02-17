package models.manage;

import models.api.OwnerableManage;
import models.api.Page;
import models.entity.Say;

import java.util.List;

/**
 * SayManage.
 *
 * @author itang
 */
public class SayManage extends OwnerableManage<Say> {
    private final String owner;
    public static final SayManage instance = new SayManage(null);

    public SayManage(String owner) {
        this.owner = owner;
    }

    public String owner() {
        return this.owner;
    }

    @Override
    public Class<Say> modelClass() {
        return Say.class;
    }

    public List<Say> getAll() {
        return getAll("-createAt");
    }

    public Page<Say> pagedAll(int currPage, int limit) {
        return page(query("-createAt"), currPage, limit);
    }

    public Say add(String content) {
        Say say = new Say(currUser().username, content);
        say.insert();
        return say;
    }

    public Say reply(String content, Say target) {
        Say say = new Say(currUser().username, content, target);

        say.insert();

        target.replys += 1;
        target.update();

        return say;
    }

    public static SayManage instance(String user) {
        return new SayManage(user);
    }

}
