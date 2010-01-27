package controllers.api;

import controllers.Application;
import models.Ownerable;
import models.User;
import play.modules.gae.GAE;
import play.mvc.Before;


public class PageController extends GaeController {
    @Before
    static void checkConnected() {
        if (GAE.getUser() == null) {
            Application.login();
        } else {
            renderArgs.put("currUser", cacheCurrUser());
        }
    }

    protected static User currUser() {
        return renderArgs.get("currUser", User.class);
    }


    protected static void checkOwner(Ownerable ownerable) {
        if (!currUser().email.equals(ownerable.owner())) {
            forbidden();
        }
    }
}
