package models;

import siena.Model;
import siena.Query;

public abstract class AbstractViewer<M extends Model> {
    protected abstract Class<M> clazz();

    protected abstract String user();

    protected Query<M> all() {
        return Model.all(this.clazz()).filter("user", this.user());
    }
}
