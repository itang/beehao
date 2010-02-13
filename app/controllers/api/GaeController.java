package controllers.api;

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
        public User get(String key) {
            return User.getOrSave(userEmail());
        }
    };

    /**
     * 是否已登录.
     *
     * @return 已登录返回true,否则false
     */
    protected static boolean isLoggedIn() {
        return GAE.isLoggedIn();
    }

    /**
     * 当前登录用户email.
     *
     * @return 返回用户email
     */
    protected static String userEmail() {
        if (isLoggedIn())
            return GAE.getUser().getEmail();
        return null;
    }

    /**
     * 当前用户.
     * <p>
     * 从缓存获取用户对象，如果不存在, 则通过用户email从持久层获取并缓存
     * </p>
     *
     * @return 返回从缓存获取用户对象
     */
    protected static User cacheCurrUser() {
        return CacheHelper.getOrCache("currUser" + userEmail(), userProvider);
    }

    /**
     * 缓存用户.
     *
     * @param currUser 用户对象
     * @return 用户
     */
    protected static User cacheCurrUser(User currUser) {
        CacheHelper.replace("currUser" + userEmail(), currUser, CacheHelper.DEFAULT_EXP);
        return currUser;
    }
}
