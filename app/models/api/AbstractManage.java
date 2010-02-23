package models.api;

import siena.Model;
import siena.Query;
import utils.LangUtil;

import java.util.List;

/**
 * AbstractManage.
 *
 * @author itang
 */
public abstract class AbstractManage<M extends Model> {

    public M get(Long id) {
        return query().filter("id", id).get();
    }

    public List<M> getAll() {
        return query().fetch();
    }

    public List<M> getAll(String order) {
        return query(order).fetch();
    }

    public Page<M> page(final Query<M> query, final int currPage, final int limit) {
        final int total = query.count();
        final int page = fromPage(currPage, total, limit);
        final int start = (page - 1) * limit;
        final List<M> items = query.fetch(limit, start);
        return new Page<M>(total, items, start, limit);
    }

    private int fromPage(int currPage, int total, int limit) {
        if (currPage == 0) return Page.DEFAULT_PAGE_START_INDEX;
        if (currPage > Page.maxPage(total, limit))
            return Page.maxPage(total, limit);
        return currPage;
    }

    public void delete(Long id) {
        get(id).delete();
    }

    public void delete(M model) {
        model.delete();
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

    protected Class<M> modelClass() {
        return getEntityClass();
    }

    private Class<M> getEntityClass() {
        return (Class<M>) LangUtil.getRawType(LangUtil.getSuperclassTypeParameter(this.getClass()));
    }

}
