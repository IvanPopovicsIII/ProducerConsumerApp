package model;

import java.util.Objects;

public class User {

	private int user_id;
	private String user_guid;
	private String user_name;
	
	public User(int user_id, String user_guid, String user_name) {
		super();
		this.user_id = user_id;
		this.user_guid = user_guid;
		this.user_name = user_name;
	}

	public int getUserId() {
		return user_id;
	}

	public String getUserGuid() {
		return user_guid;
	}

	public String getUserName() {
		return user_name;
	} 
	
	 @Override
	    public String toString() {
	        return "User [user_id=" + user_id + ", user_guid=" + user_guid + ", user_name=" + user_name + "]";
	    }
	
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        User user = (User) o;
	        return user_id == user.user_id &&
	               Objects.equals(user_guid, user.user_guid) &&
	               Objects.equals(user_name, user.user_name);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(user_id, user_guid, user_name);
	    }
	 
}
