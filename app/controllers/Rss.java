package controllers;

import models.entity.Say;
import models.manage.SayManage;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;


/**
 * RSS 发布 Action.
 *
 * @author itang
 */
public class Rss extends Controller {

    public static void says() {
        final String user = params.get("user");
        List<Say> says = user == null ? SayManage.instance.getAll() : SayManage.instance(user).getAll();

        renderArgs.put("says", says);
        renderArgs.put("pubDate", new Date(new Date().getTime() - 1000 * 3600 * 24));
        renderArgs.put("lastBuildDate", new Date());

        render();
    }
}
