package controllers;

import models.Bookmarker;
import models.Config;
import myutils.ResultBuilder;
import play.modules.gae.GAE;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.results.Forbidden;

/**
 * 我的主页Action.
 */
public class Site extends Controller {
    @Before
    static void checkConnected() {
        if (GAE.getUser() == null) {
            Application.login();
        } else {
            renderArgs.put("user", GAE.getUser().getEmail());
        }
    }

    /**
     * 主页.
     */
    public static void index() {
        String homepage = Config.getHomepage(getUser());
        if (homepage == null) homepage = "http://www.javaeye.com";

        renderArgs.put("homepage", homepage);
        renderArgs.put("bookmarkers", Bookmarker.viewer(getUser()).getAll());
        render();
    }

    /**
     * 更新bookmarker点击量.
     */
    public static void update_hit(Long id) {
        if (Request.current().method.equals("POST")) {
            Bookmarker bookmarker = Bookmarker.getById(id);

            notFoundIfNull(bookmarker);
            checkOwner(bookmarker);

            Bookmarker b = Bookmarker.increaseOneHit(bookmarker);

            renderJSON(ResultBuilder.success().msg("OK").value("currHit", b.hit).toJson());
        } else {
            throw new Forbidden("不允许get请求!");
        }
    }

    static String getUser() {
        return renderArgs.get("user", String.class);
    }

    static void checkOwner(Bookmarker bookmarker) {
        if (!getUser().equals(bookmarker.user)) {
            forbidden();
        }
    }
}
