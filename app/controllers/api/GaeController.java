package controllers.api;

import models.User;
import utils.CacheHelper;
import play.modules.gae.GAE;
import play.mvc.Controller;

import utils.CacheHelper.Provider;

public class GaeController extends Controller {
    private static final Provider<User> userProvider = new CacheHelper.Provider<User>() {
        public User get(String key) {
            return User.getOrSave(userEmail());
        }
    };

    protected static boolean isLoggedIn() {
        return GAE.isLoggedIn();
    }

    protected static String userEmail() {
        if (isLoggedIn())
            return GAE.getUser().getEmail();
        return null;
    }

    protected static User cacheCurrUser() {
        return CacheHelper.getOrCache("currUser" + userEmail(), userProvider);
    }

    protected static User cacheCurrUser(User currUser) {
        CacheHelper.replace("currUser" + userEmail(), currUser, CacheHelper.DEFAULT_EXP);
        return currUser;
    }


}
