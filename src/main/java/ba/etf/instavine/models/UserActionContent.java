package ba.etf.instavine.models;

import java.util.Date;

public class UserActionContent extends UserAction
{
	private int id;
	
	private Content content;
	
	private Date updatedAt, createdAt;
	
	public UserActionContent() {
	   	
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Content getContent() {
		return content;
	}
	
	public void setContent(Content content) {
		this.content = content;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatdAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
