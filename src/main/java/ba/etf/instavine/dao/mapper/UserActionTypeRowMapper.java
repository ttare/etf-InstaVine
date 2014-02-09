package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.UserActionType;

public class UserActionTypeRowMapper implements RowMapper 
{
	@Override
	public Object map(ResultSet rs) throws SQLException 
	{
		UserActionType userActionType = new UserActionType(); 
		
		userActionType.setId(rs.getInt(1));
		userActionType.setType(rs.getString(2));
		userActionType.setUpdatedAt(rs.getTimestamp(3));
		userActionType.setCreatedAt(rs.getTimestamp(4));
		
		return userActionType;
	}
}
