package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserActionContentDao;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.dao.mapper.UserActionContentRowMapper;
import ba.etf.instavine.models.UserActionContent;
import ba.etf.instavine.utils.DaoUtil;


public class UserActionContentDaoImpl implements UserActionContentDao
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new UserActionContentRowMapper();
	
	
	public UserActionContentDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	@Override
	public List<UserActionContent> findAll(int offset, int limit) 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM UserActionContents LIMIT ?, ?",
									offset,
									limit);
	}

	@Override
	public UserActionContent find(int id) 
	{
		Connection connection = daoFactory.getConnection();

		return DaoUtil.executeQueryReturnOne(connection, rowMapper, "SELECT * FROM UserActionContents WHERE id = ?", id);
	}

	@Override
	public boolean insert(UserActionContent userActionContent)
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO UserActionContents (contents_id, useractions_id) VALUES (?, ?)",
										  userActionContent.getContent().getId());
		
		userActionContent.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(UserActionContent userActionContent) 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE UserActionContents SET contents_id = ?, useractions_id = ? WHERE id = ?",
							  userActionContent.getContent().getId(),
							  userActionContent.getId());
		
		return true;
	}

	@Override
	public boolean delete(UserActionContent userActionContent) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM UserActionContents WHERE id = ?", userActionContent.getId());
		
		return true;
	}
	
}
