package controllers;

import controllers.api.PageController;
import models.entity.Bookmarker;
import models.entity.Config;
import models.manage.BookmarkerManage;
import play.mvc.Http.Request;
import play.mvc.results.Forbidden;
import utils.ResultBuilder;


/**
 * 我的主页Action.
 */
public class Bookmarkers extends PageController {
    /**
     * 主页.
     */
    public static void index() {
        String homepage = Config.getHomepage(currUser().email);
        if (homepage == null) homepage = "http://www.javaeye.com";

        renderArgs.put("homepage", homepage);
        renderArgs.put("bookmarkers", ownerBookmarkerManage().getAll());
        render();
    }


    /**
     * 更新bookmarker点击量.
     *
     * @param id 书签id
     */
    public static void update_hit(Long id) {
        if (Request.current().method.equals("POST")) {
            Bookmarker bookmarker = bookmarkerManage().get(id);

            notFoundIfNull(bookmarker);
            checkOwner(bookmarker);

            Bookmarker b = bookmarkerManage().increaseOneHit(bookmarker);

            renderJSON(ResultBuilder.success().msg("OK").value("currHit", b.hit).toJson());
        } else {
            throw new Forbidden("不允许get请求!");
        }
    }

    private static BookmarkerManage ownerBookmarkerManage() {
        return BookmarkerManage.instance(currUser().email);
    }

    private static BookmarkerManage bookmarkerManage() {
        return BookmarkerManage.instance();
    }

}
