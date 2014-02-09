package ba.etf.instavine.service;

import ba.etf.instavine.models.User;


public interface LoginService 
{
	public boolean validateSession(String username, String key);
	
	public String encryptPassword(String password);
	
	public User getCurrentUser();
	
}
