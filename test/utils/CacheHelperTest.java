package utils;

import play.test.UnitTest;
import org.junit.*;

/**
 * CacheHelperTest.
 *
 * @author itang
 */
public class CacheHelperTest extends UnitTest {
    @Test
    public void test_GetOrCache_String_Object() {
        CacheHelper.set("key", "world");
        assertEquals(CacheHelper.getOrCache("key", "hello"), "world");
        CacheHelper.delete("key");
        String value = (String) CacheHelper.getOrCache("key", "hello");
        assertEquals(value, "hello");
    }
}
