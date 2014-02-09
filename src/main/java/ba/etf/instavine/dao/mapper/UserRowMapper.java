package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.User;

public class UserRowMapper implements RowMapper 
{
	@Override
	public Object map(ResultSet rs) throws SQLException 
	{	
		User user = new User();
		
		user.setId(rs.getInt(1));
		user.setUsername(rs.getString(2));
		user.setEmail(rs.getString(3));
		user.setDateOfBirth(rs.getDate(4));
		user.setPassword(rs.getString(5));
		user.setSalt(rs.getInt(6));
		user.setUpdatedAt(rs.getTimestamp(7));
		user.setCreatedAt(rs.getTimestamp(8));
		
		return user;
	}
}
