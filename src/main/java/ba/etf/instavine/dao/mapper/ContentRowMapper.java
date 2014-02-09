package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.FilterType;
import ba.etf.instavine.models.User;

public class ContentRowMapper implements RowMapper 
{
	@Override
	public Object map(ResultSet rs) throws SQLException 
	{
		Content content = new Content();
		
		content.setId(rs.getInt(1));
		content.setTitle(rs.getString(2));
		content.setDescription(rs.getString(3));

		content.setLength(rs.getTime(4));
		
		User user = new User();
		user.setId(rs.getInt(5));

		FilterType filterType = new FilterType();
		filterType.setId(rs.getInt(6));
		content.setFilterType(filterType);

		content.setUpdatedAt(rs.getTimestamp(7));
		content.setCreatedAt(rs.getTimestamp(8));
		
		return content;
	}
}
