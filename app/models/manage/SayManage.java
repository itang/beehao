package models.manage;

import models.api.OwnerableManage;
import models.entity.Say;
import models.entity.User;

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
        return query().order("-createAt").fetch();
    }

    public Say add(String content) {
        Say say = new Say(owner(), content);
        say.nickname = currUser().nickname;
        say.insert();

        return say;
    }

    public Say reply(String content, Say target) {
        Say say = new Say(owner(), content, target);
        User replyToUser = User.get(target.user);

        say.replyTo = replyToUser.nickname != null ? replyToUser.nickname : replyToUser.email;
        say.nickname = currUser().nickname;
        say.insert();

        target.replys += 1;
        target.update();

        return say;
    }

    public static SayManage instance(String user) {
        return new SayManage(user);
    }
}
