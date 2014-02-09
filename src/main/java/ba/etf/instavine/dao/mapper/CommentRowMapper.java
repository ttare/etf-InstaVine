package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.Comment;
import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.User;

public class CommentRowMapper implements RowMapper 
{
	@Override
	public Object map(ResultSet rs) throws SQLException 
	{
		Comment comment = new Comment();
		
		comment.setId(rs.getInt(1));
		comment.setText(rs.getString(2));
		
		Content content = new Content();
		content.setId(rs.getInt(3));
		comment.setContent(content);
		
		User user = new User();
		user.setId(rs.getInt(4));
		comment.setUser(user);
		
		comment.setUpdatedAt(rs.getTimestamp(5));
		comment.setCreatedAt(rs.getTimestamp(6));
						
		return comment;
	}
}
