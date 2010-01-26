package controllers;

import models.Say;
import models.SayViewer;
import myutils.ResultBuilder;
import play.modules.gae.GAE;
import play.mvc.Before;
import play.mvc.Controller;

import java.util.List;


public class Says extends Controller {

    public static void index() {
        renderArgs.put("says", Say.getAll());
        // renderArgs.put("mysays", Says.viewer(getUser()).getAll());
        render();
    }

    public static void add() {
        String content = params.get("content");

        Say say = Says.viewer(getUser()).add(content);
        renderJSON(ResultBuilder.success().msg("心说成功!").data(say).toJson());
    }

    public static void delete(Long id) {
        Say say = Says.viewer(getUser()).get(id);
        if (say != null)
            say.delete();

        renderJSON(ResultBuilder.success().msg("删除成功!").toJson());
    }

    public static void reply() {
        String content = params.get("content");
        Long targetId = params.get("target", Long.class);
        Say target = Say.get(targetId);
        if (target == null) {
            renderJSON(ResultBuilder.failure().msg("出错了,回复对象不存在!").toJson());
        }

        Says.viewer(getUser()).reply(content, target);


        renderJSON(ResultBuilder.success().msg("回复成功!").value("replys", target.replys).toJson());
    }

    @Before()
    static void checkConnected() {
        if (GAE.getUser() == null) {
            Application.login();
        } else {
            renderArgs.put("user", GAE.getUser().getEmail());
        }
    }

    private static SayViewer viewer(String user) {
        return new SayViewer(user);
    }

    static String getUser() {
        return renderArgs.get("user", String.class);
    }
}
