package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.CommentDao;
import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.mapper.CommentRowMapper;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.models.Comment;
import ba.etf.instavine.utils.DaoUtil;


public class CommentDaoImpl implements CommentDao 
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new CommentRowMapper();
	
	
	public CommentDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	@Override
	public List<Comment> findAll(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Comments LIMIT ?, ?",
									offset,
									limit);
	}
	
	@Override
	public List<Comment> findAllByContent(int contentId, int offset, int limit) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper,
									"SELECT * FROM Comments WHERE contents_id = ? ORDER BY id DESC LIMIT ?, ?", 
									contentId,
									offset,
									limit);
	}
	
	@Override
	public Comment find(int id)  throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, 
											 rowMapper, 
											 "SELECT * FROM Comments WHERE id = ?", 
											 id);
	}
	
	@Override
	public boolean insert(Comment comment) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO Comments (text, contents_id, users_id) VALUES (?, ?, ?)",
										  comment.getText(),
										  comment.getContent().getId(),
										  comment.getUser().getId());

		comment.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(Comment comment) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE Comments SET text = ?, contents_id = ?, users_id = ? WHERE id = ?",
							  comment.getText(),
							  comment.getContent().getId(),
							  comment.getUser().getId(),
							  comment.getId());
		
		return true;
	}

	@Override
	public boolean delete(Comment comment) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM Comments WHERE id = ?", comment.getId());
		
		return true;
	}
	
}
