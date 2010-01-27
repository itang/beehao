package controllers;

import play.mvc.Http.Request;
import play.mvc.results.Forbidden;

import controllers.api.PageController;
import models.Bookmarker;
import models.Config;
import utils.ResultBuilder;


/**
 * 我的主页Action.
 */
public class Site extends PageController {
    /**
     * 主页.
     */
    public static void index() {
        String homepage = Config.getHomepage(currUser().email);
        if (homepage == null) homepage = "http://www.javaeye.com";

        renderArgs.put("homepage", homepage);
        renderArgs.put("bookmarkers", Bookmarker.viewer(currUser().email).getAll());
        render();
    }

    /**
     * 更新bookmarker点击量.
     *
     * @param id 书签id
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


}
