package models.api;

import siena.Model;
import siena.Query;

import java.util.List;

/**
 * AbstractManage.
 *
 * @author itang
 */
public abstract class AbstractManage<M extends Model> {
    public abstract Class<M> modelClass();

    public M get(Long id) {
        return query().filter("id", id).get();
    }

    public List<M> getAll() {
        return query().fetch();
    }

    public List<M> getAll(String order) {
        return query().order(order).fetch();
    }

    public void delete(Long id) {
        get(id).delete();
    }

    public AbstractManage<M> deleteAll() {
        for (M m : getAll()) {
            m.delete();
        }

        return this;
    }

    public Query<M> query() {
        return Model.all(modelClass());
    }
}
