package controllers;


import controllers.api.PageController;
import models.entity.Say;
import models.manage.SayManage;
import org.apache.commons.lang.time.DateFormatUtils;
import utils.ResultBuilder;

import java.util.Arrays;


public class Says extends PageController {
    public static void index() {
        renderArgs.put("says", SayManage.instance.getAll());
        System.out.println(Arrays.toString(Say.class.getTypeParameters()));

        render();
    }

    public static void add() {
        String content = params.get("content");

        Say say = ownerSayManage().add(content);
        renderJSON(ResultBuilder.success().msg("心说成功!")
                .value("id", say.id).value("replys", say.replys)
                .value("user", say.user)
                .value("content", say.content)
                .value("createAt", DateFormatUtils.format(say.createAt, "yyyy-MM-dd HH:mm:ss"))
                .toJson());
    }

    public static void delete(Long id) {
        Say say = ownerSayManage().get(id);
        if (say != null)
            say.delete();

        renderJSON(ResultBuilder.success().msg("删除成功!").toJson());
    }

    public static void reply() {
        String content = params.get("content");
        Long targetId = params.get("target", Long.class);
        Say target = SayManage.instance.get(targetId);
        if (target == null) {
            renderJSON(ResultBuilder.failure().msg("出错了,回复对象不存在!").toJson());
        } else {
            ownerSayManage().reply(content, target);
            renderJSON(ResultBuilder.success().msg("回复成功!").value("replys", target.replys).toJson());
        }
    }

    private static SayManage ownerSayManage() {
        return SayManage.instance(currUser().email);
    }

}
