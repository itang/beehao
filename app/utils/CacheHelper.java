package utils;

import play.cache.Cache;


public final class CacheHelper extends Cache {
    public static final String DEFAULT_EXP = "30mn";

    public static Object getOrCache(final String key, final Object value) {
        Object old = Cache.get(key);
        if (old == null) {
            if (value instanceof Provider) {
                Object v = Provider.class.cast(value).get(key);
                Cache.set(key, v, DEFAULT_EXP);
                return v;
            } else {
                Cache.set(key, value, DEFAULT_EXP);
                return value;
            }
        }
        return old;
    }

    public static <T> T getOrCache(String key, Provider<T> provider) {
        T old = (T) Cache.get(key);
        if (old == null) {
            T v = provider.get(key);
            Cache.set(key, v, DEFAULT_EXP);
            return v;
        }
        return old;
    }

    public static <T> T getOrCache(String key, T value, Class<T> clazz) {
        T old = Cache.get(key, clazz);
        if (old == null) {
            Cache.set(key, value, DEFAULT_EXP);
            return value;
        }
        return old;
    }

    public static interface Provider<T> {
        T get(String key);
    }
}
