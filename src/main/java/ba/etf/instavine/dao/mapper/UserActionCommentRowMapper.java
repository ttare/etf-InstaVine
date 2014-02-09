package ba.etf.instavine.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ba.etf.instavine.models.Comment;
import ba.etf.instavine.models.UserActionComment;

public class UserActionCommentRowMapper implements RowMapper 
{
	@Override
	public Object map(ResultSet rs) throws SQLException 
	{
		UserActionComment userActionComment = new UserActionComment();
		
		userActionComment.setId(rs.getInt(1));
		
		Comment comment = new Comment();
		comment.setId(rs.getInt(2));
		userActionComment.setComment(comment);
		
		userActionComment.setUpdatedAt(rs.getTimestamp(3));
		userActionComment.setCreatedAt(rs.getTimestamp(4));
				
		return userActionComment;
	}
}
