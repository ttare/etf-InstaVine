package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.Tag;

public class TagRowMapper implements RowMapper
{
	@Override
	public Object map(ResultSet rs) throws SQLException
	{
		Tag tag = new Tag();
		
		tag.setId(rs.getInt(1));
		tag.setName(rs.getString(2));
		tag.setUpdatedAt(rs.getTimestamp(3));
		tag.setCreatedAt(rs.getTimestamp(4));
		
		return tag;
	}
}
