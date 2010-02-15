package controllers.api;

import controllers.Application;
import controllers.Users;
import models.api.Ownerable;
import models.api.Page;
import models.entity.User;
import play.modules.gae.GAE;
import play.mvc.Before;
import utils.RenderRss;


public class PageController extends GaeController {
    @Before(unless = "rss")
    static void checkConnected() {
        if (!isLoggedIn()) {
            session.put("forward", request.url);
            Users.login();
        } else {
            renderArgs.put("currUser", cacheCurrUser());
        }
    }

    protected static User currUser() {
        return renderArgs.get("currUser", User.class);
    }


    protected static void checkOwner(Ownerable ownerable) {
        if (!currUser().email.equals(ownerable.owner())) {
            forbidden();
        }
    }

    protected static void renderRss(String xml) {
        throw new RenderRss(xml);
    }

    protected static int start() {
        return p("start", Integer.class, Page.DEFAULT_START);
    }

    protected static int limit() {
        return limit(Page.DEFAULT_LIMIT);
    }

    protected static int limit(int defaultValue) {
        return p("limit", Integer.class, defaultValue);
    }

    protected static Object p(String key, Object defaultValue) {
        Object t = params.get(key);
        return t == null ? defaultValue : t;
    }

    protected static <T> T p(String key, Class<T> clazz, T defaultValue) {
        T t = params.get(key, clazz);
        return t == null ? defaultValue : t;
    }


}
