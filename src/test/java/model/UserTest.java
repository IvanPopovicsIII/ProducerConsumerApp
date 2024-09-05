package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        User user = new User(1, "a1", "Robert");

        assertEquals(1, user.getUserId());
        assertEquals("a1", user.getUserGuid());
        assertEquals("Robert", user.getUserName());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User(1, "a1", "Robert");
        User user2 = new User(1, "a1", "Robert");
        User user3 = new User(2, "a2", "Martin");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

}