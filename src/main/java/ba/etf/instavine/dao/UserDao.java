package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.User;

public interface UserDao
{
	public List<User> findAll(int offset, int limit) throws DaoException;
	
	public User find(int id) throws DaoException;
	
	public User find(String usernameOrEmail) throws DaoException;
	
	public boolean insert(User user) throws DaoException;
	
	public boolean update(User user) throws DaoException;
	
	public boolean delete(User user) throws DaoException;
	
	public int count() throws DaoException;
	
	public void logActionLogin(User user) throws DaoException;
	
	public void logActionContentWatch(User user, int contentId) throws DaoException;
	
	public void logActionContentMark(User user, int contentId, int mark) throws DaoException;
}

