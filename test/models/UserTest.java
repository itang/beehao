package models;

import models.entity.User;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

/**
 * models.UserTest.
 *
 * @author itang
 */
public class UserTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAll();

        try {
            Fixtures.load("models/user.yml");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void getUser() {
        System.out.println(User.all(User.class).fetch());
        User user = User.get("bob@gmail.com");
        assertNotNull(user);
        assertEquals(user.nickname, null);
    }
}
