package controllers;

import models.Say;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class Rss extends Controller {

    public static void says() {
        final String user = params.get("user");
        List<Say> says = user == null ? Say.getAll() : Say.viewer(user).getAll();

        renderArgs.put("says", says);
        renderArgs.put("pubDate", new Date(new Date().getTime() - 1000 * 3600 * 24));
        renderArgs.put("lastBuildDate", new Date());

        render();
    }
}
