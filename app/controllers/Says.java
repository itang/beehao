package controllers;


import controllers.api.PageController;
import models.api.Page;
import models.entity.Say;
import models.entity.User;
import models.manage.SayManage;
import play.data.validation.Required;
import play.data.validation.Validation;

import java.util.Date;
import java.util.List;


public class Says extends PageController {

    public static void index(int page) {
        Page<Say> says = SayManage.instance.pagedAll(page, limit(30));

        render(says);
    }

    public static void user(@Required String user, int page) {
        user = user == null ? currUsername() : user;
        renderArgs.put("host", User.get(user));
        renderArgs.put("says", ownerSayManage().pagedAll(page, limit(15)));

        render();
    }

    public static void say(@Required String content) {
        if (Validation.hasErrors()) {
            flash.error("你想说什么?!");
        } else {
            Say say = ownerSayManage().add(content);
            flash.success("成功发表你的心说!");
        }

        toUserHome();
    }

    public static void delete(@Required Long id) {
        Say say = ownerSayManage().get(id);
        if (say != null)
            say.delete();

        flash.success("成功删除!");
        toUserHome();
    }

    public static void reply(@Required Long id, @Required String content) {
        if (Validation.hasErrors()) {
            flash.error("请输入回复内容!");
            show(id);
        }
        final Say target = SayManage.instance.get(id);

        if (target == null) {
            flash.error("null");
            toUserHome();
        }
        ownerSayManage().reply(content, target);

        flash.success("回复成功!");
        show(id);
    }


    public static void rss() {
        final String user = params.get("user");
        List<Say> says = user == null ? SayManage.instance.getAll() : SayManage.instance(user).getAll();

        renderArgs.put("says", says);
        renderArgs.put("pubDate", new Date(new Date().getTime() - 1000 * 3600 * 24));
        renderArgs.put("lastBuildDate", new Date());

        render();
    }

    public static void show(@Required Long id) {
        Say target = SayManage.instance.get(id);

        List<Say> replys = target.replies();
        render(target, replys);
    }

    private static void toUserHome() {
        user(currUsername(), Page.DEFAULT_PAGE_START_INDEX);
    }

    private static SayManage ownerSayManage() {
        return SayManage.instance(currUsername());
    }

}
