package controllers;

import controllers.api.GaeController;

import play.modules.gae.*;


/**
 * Application Web 应用入口.
 *
 * @author itang
 */
public class Application extends GaeController {
    public static void index() {
        if (isLoggedIn()) {
            //同步用户
            cacheCurrUser();
            Says.index();
        } else {
            render();
        }
    }

    public static void login() {
        GAE.login("Application.index");
    }

    public static void logout() {
        GAE.logout("Application.index");
    }
}
