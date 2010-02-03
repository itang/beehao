package models;

import siena.Model;
import siena.Query;
import utils.CacheHelper;

public abstract class AbstractViewer<M extends Model> {
    protected abstract Class<M> clazz();

    protected abstract String user();

    protected Query<M> all() {
        return Model.all(this.clazz()).filter("user", this.user());
    }

    protected User currUser() {
        User user = CacheHelper.get("currUser" + user(), User.class);
        if (user == null)
            user = User.get(user());
        return user;
    }
}
