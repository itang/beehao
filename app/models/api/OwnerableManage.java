package models.api;

import models.entity.User;
import siena.Model;
import siena.Query;
import utils.CacheHelper;

/**
 * 带Owner的管理类.  
 *
 * @param <M>  Model 类
 */
public abstract class OwnerableManage<M extends Model> extends AbstractManage<M> implements Ownerable<String> {

    protected User currUser() {
        User user = CacheHelper.get("currUser" + owner(), User.class);
        if (user == null)
            user = User.get(owner());
        return user;
    }

    @Override
    protected Query<M> query() {
        if (this.owner() == null) return super.query();

        return super.query().filter("username", this.owner());
    }

    @Override
    protected Query<M> query(String order) {
        return order == null ? query() : query().order(order);
    }
}
