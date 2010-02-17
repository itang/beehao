package controllers.api;

import controllers.Users;
import models.entity.User;
import play.modules.gae.GAE;
import play.mvc.Controller;
import utils.CacheHelper;
import utils.CacheHelper.Provider;

/**
 * GAE Action支持.
 *
 * @author itang
 */
public class GaeController extends Controller {
    private static final Provider<User> userProvider = new CacheHelper.Provider<User>() {
        public User get(String user) {
            return User.getOrSave(currUsername());
        }
    };

    /**
     * 是否已登录.
     *
     * @return 已登录返回true,否则false
     */
    protected static boolean isLoggedIn() {
        return isLocalLoggedIn() || isGaeLoggedIn();
    }

    /**
     * 是否从本站登录?
     *
     * @return true 如果从本站输入用户、密码登录
     */
    protected static boolean isLocalLoggedIn() {
        return session.get("user") != null ;
    }

    /**
     * 是否来自GAE Google 帐户的登录.
     *
     * @return true 如果来自Google 登录
     */
    protected static boolean isGaeLoggedIn() {
        return GAE.isLoggedIn();
    }

    /**
     * 当前登录用户email.
     *
     * @return 返回用户email
     */
    protected static String currUsername() {
        if (isLocalLoggedIn()) {
            return session.get("user");
        }
        return GAE.getUser().getEmail().replace("@", "AT");
    }

    /**
     * 当前用户对象.
     * <p>
     * 以用户email标识，从缓存获取用户对象；如果不存在, 则通过email从持久层获取并缓存
     * </p>
     *
     * @return 返回从缓存获取用户对象
     */
    protected static User cacheCurrUser() {
        return CacheHelper.getOrCache("currUser" + currUsername(), userProvider);
    }

    /**
     * 缓存用户.
     *
     * @param currUser 用户对象
     * @return 用户
     */
    protected static User cacheCurrUser(User currUser) {
        CacheHelper.replace("currUser" + currUsername(), currUser, CacheHelper.DEFAULT_EXP);
        return currUser;
    }
}
