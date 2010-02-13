package controllers.api;

import controllers.Application;
import models.api.Ownerable;
import models.entity.User;
import play.modules.gae.GAE;
import play.mvc.Before;
import utils.RenderRss;


public class PageController extends GaeController {
    @Before(unless = "rss")
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

    protected static void renderRss(String xml) {
        throw new RenderRss(xml);
    }

}
