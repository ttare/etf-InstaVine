package ba.etf.instavine.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserDao;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.dao.mapper.UserRowMapper;
import ba.etf.instavine.models.User;
import ba.etf.instavine.utils.DaoUtil;

public class UserDaoImpl implements UserDao
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new UserRowMapper();
	
	public UserDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public List<User> findAll(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Users LIMIT ?, ?", 
									offset, 
									limit);
	}	

	@Override
	public User find(int id) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, 
											 rowMapper, 
											 "SELECT * FROM Users WHERE id = ?", 
											 id);
	}
	
	@Override
	public User find(String usernameOrEmail) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, 
											 rowMapper, 
											 "SELECT * FROM Users WHERE username = ? OR email = ?", 
											 usernameOrEmail, 
											 usernameOrEmail);
	}

	@Override
	public boolean insert(User user) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO Users (lastName, firstName, username, email, phone, dateOfBirth, states_id, password, salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
										  user.getUsername(),
										  user.getEmail(),
										  DaoUtil.utilDate2SqlDatw(user.getDateOfBirth()),
										  user.getPassword(),
										  user.getSalt());
		
		user.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(User user) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE Users SET lastName = ?, firstName = ?, username = ?, email = ?, phone = ?, dateOfBirth = ?, states_id = ?, password = ?, salt = ? WHERE id = ?",
							  user.getUsername(),
							  user.getEmail(),
							  DaoUtil.utilDate2SqlDatw(user.getDateOfBirth()),
							  user.getPassword(),
							  user.getSalt(),
							  user.getId());
		
		DaoUtil.executeUpdate(connection, 
	  			  			  "DELETE IGNORE FROM UserGroups WHERE users_id = ?",
	  			  			  user.getId());
		
		DaoUtil.executeUpdate(connection, 
	  			  			  "DELETE IGNORE FROM UserRoles WHERE users_id = ?",
	  			  			  user.getId());
		
		return true;
	}
	
	@Override
	public boolean delete(User user) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM Users WHERE id = ?", user.getId());
		
		return true;
	}

	@Override
	public int count() throws DaoException {
		Connection connection = daoFactory.getConnection();
		
		ResultSet rs = DaoUtil.executeQuery(connection, "SELECT COUNT(*) FROM Users");
		
		int count = 0;
		try {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}


	@Override
	public void logActionLogin(User user) throws DaoException {
		Connection connection = daoFactory.getConnection();
		
		try {
			CallableStatement cs = connection.prepareCall("{call register_login(?)}");
		
			cs.setInt(1, user.getId());

			cs.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void logActionContentWatch(User user, int contentId) throws DaoException {
		Connection connection = daoFactory.getConnection();
		
		try {
			CallableStatement cs = connection.prepareCall("{call register_content_watch(?, ?)}");
		
			cs.setInt(1, user.getId());
			cs.setInt(2, contentId);
			
			cs.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void logActionContentMark(User user, int contentId, int mark) throws DaoException {
		Connection connection = daoFactory.getConnection();
		
		try {
			CallableStatement cs = connection.prepareCall("{call register_content_mark(?, ?, ?)}");
		
			cs.setInt(1, user.getId());
			cs.setInt(2, contentId);
			cs.setInt(3, mark);
			
			cs.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
