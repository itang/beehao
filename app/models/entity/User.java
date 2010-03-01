package models.entity;

import play.data.validation.Email;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Unique;
import utils.CacheHelper;

import java.io.Serializable;

import static utils.StringUtil.nvl;

/**
 * User 用户.
 *
 * @author itang
 */
public class User extends Model implements Serializable {
    @Id
    public Long id;
    //用户email,唯一标识
    @Email
    public String email;
    //用户标识
    public String username;
    //昵称
    public String nickname;
    //密码
    public String password;

    public User() {
        //
    }

    public User(String username) {
        this.username = username;
    }

    public User(String email, String username, String nickname, String password) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    private static Query<User> all() {
        return Model.all(User.class);
    }

    public String screenName() {
        return nvl(nickname, username);
    }

    public static User getOrSave(String username) {
        User user = get(username);
        if (user == null) {
            user = new User(username);
            user.insert();
        }

        return user;
    }


    public static User get(String username) {
        return all().filter("username", username).get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
