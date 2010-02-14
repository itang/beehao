package controllers;

import controllers.api.GaeController;
import models.entity.User;
import play.data.validation.*;
import play.mvc.Controller;

/**
 * Users.
 *
 * @author itang
 */
public class Users extends GaeController {
    public static void regist() {
        render();
    }

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

    public static void signon_success() {
        render();
    }

    public static void login() {
        render();
    }

    public static void logon(@Required @Email String email, @Required @Password String password) {
        if (Validation.hasErrors()) {
            flash.error("请输入正确的email或密码!");
            login();
        }
        User user = User.get(email);
        if (user == null || password.equals(user.password)) {
            flash.error("用户不存在或密码有误!");
            flash.put("email", email);
            login();
        }

        session.put("user", email);
        Application.index();
    }

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
