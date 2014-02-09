package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserActionTypeDao;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.dao.mapper.UserActionTypeRowMapper;
import ba.etf.instavine.models.UserActionType;
import ba.etf.instavine.utils.DaoUtil;


public class UserActionTypeDaoImpl implements UserActionTypeDao 
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new UserActionTypeRowMapper();
	
	
	public UserActionTypeDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	@Override
	public List<UserActionType> findAll(int offset, int limit)
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM UserActionTypes LIMIT ?, ?",
									offset,
									limit);
	}

	@Override
	public UserActionType find(int id) 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, rowMapper, "SELECT * FROM UserActionTypes WHERE id = ?", id);
	}

	@Override
	public boolean insert(UserActionType userActionType) 
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, "INSERT INTO UserActionTypes (type) VALUES (?)", userActionType.getType());
		
		userActionType.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(UserActionType userActionType) 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "UPDATE UserActionTypes SET type = ? WHERE id = ?", userActionType.getType(), userActionType.getId());
		
		return true;
	}

	@Override
	public boolean delete(UserActionType userActionType) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM UserActionTypes WHERE id = ?", userActionType.getId());
		
		return false;
	}

}
