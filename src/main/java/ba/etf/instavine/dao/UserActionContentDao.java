package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.UserActionContent;

public interface UserActionContentDao
{
	public List<UserActionContent> findAll(int offset, int limit) throws DaoException;
	
	public UserActionContent find(int id) throws DaoException;
	
	public boolean insert(UserActionContent userActionContent) throws DaoException;
	
	public boolean update(UserActionContent userActionContent) throws DaoException;
	
	public boolean delete(UserActionContent userActionContent) throws DaoException;
}