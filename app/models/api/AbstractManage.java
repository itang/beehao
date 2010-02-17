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
        return query(order).fetch();
    }

    public Page<M> page(Query<M> query, int currPage, int limit) {
        final int total = query.count();
        if (currPage == 0) currPage = 1;
        final int start = (currPage - 1) * limit;
        final List<M> items = query.fetch(limit, start);
        return new Page<M>(total, items, start, limit);
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

    protected Query<M> query() {
        return Model.all(modelClass());
    }

    protected Query<M> query(String order) {
        final Query<M> query = Model.all(modelClass());
        if (order != null) {
            query.order(order);
        }
        return query;
    }
}
