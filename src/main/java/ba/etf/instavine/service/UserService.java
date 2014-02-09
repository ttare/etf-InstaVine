package ba.etf.instavine.service;

import java.util.List;

import ba.etf.instavine.models.User;
import ba.etf.instavine.models.UserAction;

public interface UserService 
{
	public List<User> findAll(int offset, int limit);
	
	public int numberOfUsers();
	
	public void insert(User user);
	
	public User find(int id);
	
	public void update(User user);
	
	public void delete(User user);
	
	public User getUser(String usernameOrEmail);
	
	public List<UserAction> getUserActions(User user, int offset, int limit);
}
