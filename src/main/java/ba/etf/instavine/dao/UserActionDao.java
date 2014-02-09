package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.UserAction;

public interface UserActionDao 
{
	public List<UserAction> findAll(int offset, int limit) throws DaoException;
	
	public List<UserAction> findAllByUser(int userId, int offset, int limit) throws DaoException;
	
	public UserAction find(int id) throws DaoException;
	
	public boolean insert(UserAction userAction) throws DaoException;
	
	public boolean update(UserAction userAction) throws DaoException;
	
	public boolean delete(UserAction userAction) throws DaoException;
}
