package controllers;

import controllers.api.GaeController;

import models.entity.TodoList;
import models.entity.User;
import models.manage.SayManage;
import models.manage.TodoListManage;
import play.modules.gae.*;

import java.util.List;


/**
 * Application Web 应用入口.
 *
 * @author itang
 */
public class Application extends GaeController {
    public static void index() {
        if (isLoggedIn()) {
            //同步用户
            synchronizeUser();
        }

        Blogs.index();
    }

    private static void synchronizeUser() {
        cacheCurrUser();
    }

    public static void login() {
        GAE.login("Application.index");
    }

    public static void logout() {
        GAE.logout("Application.index");
    }
}
