package controllers;

import controllers.api.GaeController;
import models.entity.User;
import org.apache.commons.lang.StringUtils;
import play.data.validation.*;
import utils.ResultBuilder;

/**
 * 用户 Action.
 *
 * @author itang
 */
public class Users extends GaeController {
    public static final String LOCAL_LOGIN_KEY = "LOCAL_LOGIN_FLAG";
    //保留的注册名
    public static final String[] reservedUsernames = {
            "user", "item", "reply"
    };

    /**
     * 注册页面.
     */
    public static void regist() {
        render();
    }

    /**
     * 注册用户.
     * <p/>
     * <p>
     * 注册用户，成功后自动登录
     * </p>
     *
     * @param username 用户名  （用户唯一标识)
     * @param email    邮件地址
     * @param nickname 昵称
     * @param password 密码
     */
    public static void signon(
            @MinSize(4) @MaxSize(20)
            String username,
            @Required @Email String email,
            @MinSize(2) @MaxSize(20)
            String nickname,
            @MinSize(6) @MaxSize(20) @Password
            String password
    ) {
        validateInputs(username, email, nickname);

        validateUsername(username, email, nickname);

        validateUserExists(username, email, nickname);

        addUser(username, email, nickname, password);

        session.put("user", username);

        flash.success(String.format("用户%s(%s)注册成功!", nickname, email));

        signon_success();
    }

    /**
     * 注册成功页面.
     */
    public static void signon_success() {
        render();
    }

    /**
     * 登录页面.
     */
    public static void login() {
        render();
    }

    /**
     * 登录.
     * <p/>
     * <p/>
     * <p>
     * 登录成功之后，如果存在之前访问页面，则直接转入，否则转入主页.
     * </p>
     *
     * @param username 用户名
     * @param password 密码
     */
    public static void logon(@Required String username, @Required @Password String password) {
        if (Validation.hasErrors()) {
            flash.error("请输入正确的email或密码!");
            login();
        }
        User user = User.get(username);
        if (user == null || !password.equals(user.password)) {
            flash.error("用户不存在或密码有误!");
            flash.put("username", username);
            login();
        }

        //标识登录
        session.put("user", user.username);

        //支持跳转
        String forward = session.get("forward");
        if (forward != null) {
            session.remove("forward");
            redirect(forward);
        }

        Application.index();
    }


    public static void checkExists(@Required String username) {
        validateUsername(username);

        if (userExists(username)) {
            renderJSON(ResultBuilder.success().value("exists", true).toJson());
        } else {
            renderJSON(ResultBuilder.success().value("exists", false).toJson());
        }

    }

    /**
     * 注销.
     */
    public static void logout() {
        if (isLocalLoggedIn()) {
            session.remove("user");
            session.clear();
            Application.index();
        } else {
            Application.logout();
        }
    }

    private static void addUser(String username, String email, String nickname, String password) {
        User user = new User();
        user.email = email;
        user.username = username;
        user.nickname = nickname;
        user.password = password;
        validation.valid(user);
        if (Validation.hasErrors()) {
            flash.error(Validation.errors().toString());
            regist();
        }
        user.insert();
    }


    private static void validateUsername(String username, String email, String nickname) {
        if (isReservedUsernames(username)) {
            flash.error(String.format("%s 为保留名,请换一个用户名再注册?!", username));
            flashs(username, email, nickname);
            regist();
        }
    }

    private static void validateUsername(String username) {
        if (isReservedUsernames(username)) {
            renderJSON(ResultBuilder.success()
                    .msg(String.format("%s 为保留名,请换一个用户名再注册?!", username))
                    .value("exists", true).toJson());
        }
    }

    private static boolean isReservedUsernames(String username) {
        return StringUtils.indexOfAny(username, reservedUsernames) != -1;
    }


    private static void validateInputs(String username, String email, String nickname) {
        if (Validation.hasErrors()) {
            flash.error("请输入正确的email、昵称、密码:" + Validation.errors());
            flashs(username, email, nickname);
            regist();
        }
    }

    private static boolean userExists(String username) {
        User user = User.get(username);
        return user != null;
    }


    private static void validateUserExists(String username, String email, String nickname) {
        if (userExists(username)) {
            flash.error("用户已经存在!");
            flashs(username, email, nickname);
            regist();
        }
    }

    private static void flashs(String username, String email, String nickname) {
        flash.put("username", username);
        flash.put("email", email);
        flash.put("nickname", nickname);
    }
}
