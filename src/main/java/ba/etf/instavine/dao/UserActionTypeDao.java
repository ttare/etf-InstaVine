package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.UserActionType;

public interface UserActionTypeDao
{
	public List<UserActionType> findAll(int offset, int limit) throws DaoException;
	
	public UserActionType find(int id) throws DaoException;
	
	public boolean insert(UserActionType userActionType) throws DaoException;
	
	public boolean update(UserActionType userActionType) throws DaoException;
	
	public boolean delete(UserActionType userActionType) throws DaoException;
}
