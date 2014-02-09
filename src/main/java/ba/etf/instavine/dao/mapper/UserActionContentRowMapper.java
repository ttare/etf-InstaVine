package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.UserActionContent;

public class UserActionContentRowMapper implements RowMapper 
{	
	@Override
	public Object map(ResultSet rs) throws SQLException
	{
		UserActionContent userActionContent = new UserActionContent();
		
		userActionContent.setId(rs.getInt(1));
		
		Content content = new Content();
		content.setId(rs.getInt(2));
		userActionContent.setContent(content);
			
		userActionContent.setUpdatedAt(rs.getTimestamp(3));
		userActionContent.setCreatedAt(rs.getTimestamp(4));
		
		return userActionContent;
	}
}
