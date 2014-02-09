package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.UserActionComment;

public interface UserActionCommentDao 
{	
	public List<UserActionComment> findAll(int offset, int limit) throws DaoException;
	
	public UserActionComment find(int id) throws DaoException;
	
	public boolean insert(UserActionComment userActionComment) throws DaoException;
	
	public boolean update(UserActionComment userActionComment) throws DaoException;
	
	public boolean delete(UserActionComment userActionComment) throws DaoException;
}
