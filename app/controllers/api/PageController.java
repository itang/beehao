package controllers.api;

import controllers.api.GaeController;
import controllers.Users;
import models.api.AbstractManage;
import models.api.Ownerable;
import models.api.Page;
import models.entity.Blog;
import models.entity.User;
import models.manage.BlogManage;
import org.apache.commons.lang.StringUtils;
import play.data.validation.Required;
import play.mvc.Before;
import siena.Model;
import utils.RenderRss;


public class PageController extends GaeController {
    public static final String[] unlessBeforeActionMethods = {
            "index",
            "index_page",
            "login",
            "rss"};

    @Before
    static void checkConnected() {
        if (!unless()) {
            if (!isLoggedIn()) {
                session.put("forward", request.url);
                flash.error("你还未登录, 请先登录!");
                Users.login();
            }
        }
        if (isLoggedIn()) {
            User currUser = cacheCurrUser();
            renderArgs.put("currUser", currUser);
            renderArgs.put("user", currUser.username);
            renderArgs.put("nickname", currUser.nickname);
            renderArgs.put("isGaeLoggedIn", isGaeLoggedIn());
        }
    }

    protected static User currUser() {
        return renderArgs.get("currUser", User.class);
    }

    protected static void checkOwner(Ownerable ownerable) {
        if (!currUsername().equals(ownerable.owner())) {
            forbidden();
        }
    }

    protected static void renderRss(String xml) {
        throw new RenderRss(xml);
    }

    protected static int start() {
        return p("start", Integer.class, Page.DEFAULT_START);
    }

    protected static int currPage() {
        return currPage(Page.DEFAULT_PAGE_START_INDEX);
    }

    protected static int currPage(int defaultValue) {
        return p("currPage", Integer.class, defaultValue);
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


    /**
     * 当前actionMethod 不需要 Before Interceptor?
     *
     * @return true 如果 方法名是unlessBeforeActionMethods其中之一
     * @NOTICE: fixed PLAY! 目前只支持只在其申明的Action 类使用unless 有效。
     */
    private static boolean unless() {
        return StringUtils.indexOfAny(request.actionMethod, unlessBeforeActionMethods) != -1;
    }

}
