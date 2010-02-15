package controllers;

import controllers.api.GaeController;
import models.entity.User;
import play.data.validation.*;

/**
 * 用户 Action.
 *
 * @author itang
 */
public class Users extends GaeController {
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
     * @param email    邮件地址 (用户唯一标识)
     * @param nickname 昵称
     * @param password 密码
     */
    public static void signon(@Required @Email String email,
                              @MinSize(4) @MaxSize(20) String nickname,
                              @MinSize(6) @MaxSize(20) @Password String password) {
        checkInputs(email, nickname);

        checkUserExists(email, nickname);

        addUser(email, nickname, password);

        session.put("user", email);

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
     * @param email    email
     * @param password 密码
     */
    public static void logon(@Required @Email String email, @Required @Password String password) {
        if (Validation.hasErrors()) {
            flash.error("请输入正确的email或密码!");
            login();
        }
        User user = User.get(email);
        if (user == null || !password.equals(user.password)) {
            flash.error("用户不存在或密码有误!");
            flash.put("email", email);
            login();
        }

        //标识登录
        session.put("user", email);

        //支持跳转
        String forward = session.get("forward");
        if (forward != null) {
            redirect(forward);
            session.remove("forward");
        }
        Application.index();
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

    private static void addUser(String email, String nickname, String password) {
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        user.password = password;
        user.insert();
    }

    private static void checkInputs(String email, String nickname) {
        if (Validation.hasErrors()) {
            flash.error("请输入正确的email、昵称、密码:" + Validation.errors());
            flash.put("email", email);
            flash.put("nickname", nickname);
            regist();
        }
    }

    private static void checkUserExists(String email, String nickname) {
        User user = User.get(email);
        if (user != null) {
            flash.error("用户已经存在!");
            flash.put("email", email);
            flash.put("nickname", nickname);
            regist();
        }
    }
}
