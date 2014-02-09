package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserActionDao;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.dao.mapper.UserActionRowMapper;
import ba.etf.instavine.models.UserAction;
import ba.etf.instavine.utils.DaoUtil;


public class UserActionDaoImpl implements UserActionDao
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new UserActionRowMapper();
	
	
	public UserActionDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	@Override
	public List<UserAction> findAll(int offset, int limit)
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM UserActions LIMIT ?, ?",
									offset,
									limit);
	}

	@Override
	public UserAction find(int id) 
	{
		Connection connection = daoFactory.getConnection();

		return DaoUtil.executeQueryReturnOne(connection, rowMapper, "SELECT * FROM UserActions WHERE id = ?", id);
	}
	
	@Override
	public boolean insert(UserAction userAction) 
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO UserActions (users_id, useractiontypes_id) VALUES (?, ?)",
										  userAction.getUser().getId(),
										  userAction.getUserActionType().getId());
		
		userAction.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(UserAction userAction)
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE UserActions SET users_id = ?, useractiontypes_id = ?, time = ? WHERE id = ?",
							  userAction.getUser().getId(),
							  userAction.getUserActionType().getId(),
							  userAction.getTime(),
							  userAction.getId());
		
		return true;
	}

	@Override
	public boolean delete(UserAction userAction) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM UserActions WHERE id = ?", userAction.getId());
		
		return true;
	}

	@Override
	public List<UserAction> findAllByUser(int userId, int offset, int limit) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM UserActions WHERE users_id = ?", 
									userId);
	}
	
}
