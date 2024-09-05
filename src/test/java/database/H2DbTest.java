package database;

import org.junit.jupiter.api.*;

import model.User;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class H2DbTest {

    private H2Db database;

    @BeforeAll
    public void setUp() {
        database = H2Db.getInstance();
    }

    @AfterEach
    public void cleanUp() {
        // Delete all users after each test to ensure isolation
        database.deleteAllUsers();
    }

    @Test
    public void testAddUser() {
        User user = new User(1, "a1", "Robert");
        database.addUser(user);

        // Verify that the user was added by fetching all users
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM SUSERS WHERE USER_ID = ?")) {

            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next());
            assertEquals("Robert", rs.getString("USER_NAME"));
        } catch (SQLException e) {
            fail("Database connection failed.");
        }
    }

    @Test
    public void testPrintAllUsers() {
        User user1 = new User(1, "a1", "Robert");
        User user2 = new User(2, "a2", "Martin");
        database.addUser(user1);
        database.addUser(user2);

        // Capture printed output
        database.printAllUsers();  // Verify manually in console output
    }

    @Test
    public void testDeleteAllUsers() {
        User user1 = new User(1, "a1", "Robert");
        User user2 = new User(2, "a2", "Martin");
        database.addUser(user1);
        database.addUser(user2);

        // Delete all users
        database.deleteAllUsers();

        // Verify the database is empty
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM SUSERS")) {

            ResultSet rs = ps.executeQuery();
            assertFalse(rs.next());
        } catch (SQLException e) {
            fail("Database connection failed.");
        }
    }
}