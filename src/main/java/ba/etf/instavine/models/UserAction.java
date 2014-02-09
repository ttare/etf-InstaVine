package ba.etf.instavine.models;

import java.util.Date;

public class UserAction 
{
	private int id;
	
	private User user;
	private UserActionType userActionType;
	private Date time;
	
	private Date updatedAt, createdAt;
	
	public UserAction() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public UserActionType getUserActionType() {
		return userActionType;
	}
	
	public void setUserActionType(UserActionType userActionType) {
		this.userActionType = userActionType;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
