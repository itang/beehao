package models;

import siena.Id;
import siena.Model;
import siena.Query;


public class User extends Model {
    @Id
    public Long id;
    public String email;
    public String nickname;

    public User() {

    }

    public User(String email) {
        this.email = email;
    }

    private static Query<User> all() {
        return Model.all(User.class);
    }

    public static User getOrSave(String email) {
        User user = get(email);
        if (user == null) {
            user = new User(email);
            user.insert();
        }

        return user;
    }

    public static User get(String email) {
        return all().filter("email", email).get();
    }
}
