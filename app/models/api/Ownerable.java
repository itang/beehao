package models.api;

import siena.Model;

public interface Ownerable<T> {
    public T owner();
}
