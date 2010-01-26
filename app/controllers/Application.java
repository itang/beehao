package controllers;

import models.User;
import play.*;

import play.mvc.*;
import play.modules.gae.*;

public class Application extends Controller {

    public static void index() {
        if (GAE.isLoggedIn()) {
            session.put("user", GAE.getUser().getEmail());
            User.getOrSave(GAE.getUser().getEmail());//同步用户

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
