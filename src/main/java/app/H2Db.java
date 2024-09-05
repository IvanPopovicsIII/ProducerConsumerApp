package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;


public class H2Db {
	
	private static final String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;USER=sa;PASSWORD=";

	private static H2Db instance = null;
	
	public H2Db() {
		initializeDatabase();
	}
	
	 public static synchronized H2Db getInstance() {
	        if (instance == null) {
	            instance = new H2Db();
	        }
	        return instance;
	    }
	
	private void initializeDatabase() {
		 String createTableSQL = "CREATE TABLE IF NOT EXISTS SUSERS ("
	                + "USER_ID INT PRIMARY KEY,"
	                + "USER_GUID VARCHAR(255),"
	                + "USER_NAME VARCHAR(255)"
	                + ");";

	        try (Connection conn = getConnection();
	             Statement stmt = conn.createStatement()) {

	            stmt.execute(createTableSQL);
	            System.out.println("Database initialized.");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	private Connection getConnection() throws SQLException { 
		return DriverManager.getConnection(jdbcUrl);
	}
		
	public void addUser(User user) {
		
		String sql = "INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getUserId());
            pstmt.setString(2, user.getUserGuid());
            pstmt.setString(3, user.getUserName());
            pstmt.executeUpdate();
            System.out.println("User added: " + user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	 public void printAllUsers() {
	        String sql = "SELECT * FROM SUSERS";

	        try (Connection conn = getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            System.out.println("Printing all users:");
	            while (rs.next()) {
	                int userId = rs.getInt("USER_ID");
	                String userGuid = rs.getString("USER_GUID");
	                String userName = rs.getString("USER_NAME");

	                System.out.println("User [user_id=" + userId + ", user_guid=" + userGuid + ", user_name=" + userName + "]");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void deleteAllUsers() {
	        String sql = "DELETE FROM SUSERS";

	        try (Connection conn = getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.executeUpdate();
	            System.out.println("All users deleted.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
	
	

