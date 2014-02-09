package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.ContentDao;
import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.mapper.ContentRowMapper;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.Tag;
import ba.etf.instavine.utils.DaoUtil;


public class ContentDaoImpl implements ContentDao
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new ContentRowMapper();
	
	
	public ContentDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	
	@Override
	public List<Content> findAll(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Contents LIMIT ?, ?",
									offset,
									limit);
	}
	
	@Override
	public List<Content> findAllByFilter(String filter, int offset, int limit) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT c.* FROM Contents c, Tags t, ContentTags ct WHERE t.id = ct.tags_id AND c.id = ct.contents_id AND t.name = ? LIMIT ?, ?",
									filter,
									offset,
									limit);
	}
	
	@Override
	public List<Content> findRecentlyAdded(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Contents ORDER BY updatedAt DESC LIMIT ?, ?",
									offset,
									limit);
	}
	
	@Override
	public List<Content> findBestRated(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT c.* " + 
									"FROM Contents c, ContentVotes cm " +
									"WHERE c.id = cm.contents_id " +
									"GROUP BY c.id " +
									"ORDER BY COUNT(cm.id) DESC " +
									"LIMIT ?, ?",
									offset,
									limit);
	}
	
	@Override
	public List<Content> findTop(int offset, int limit) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Contents c WHERE top = 1 LIMIT ?, ?",
									offset,
									limit);
	}

	@Override
	public Content find(int id) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, rowMapper, "SELECT * FROM Contents WHERE id = ?", id);
	}

	@Override
	public boolean insert(Content content) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO Contents (title, description) VALUES (?, ?)",
										  content.getTitle(),
										  content.getDescription());
		
		content.setId(rowId);
		
		for(Tag tag : content.getTags()) {
			DaoUtil.executeUpdate(connection, "INSERT INTO ContentTags(contents_id, tags_id) VALUES(?, ?)", content.getId(), tag.getId());
		}

		return true;
	}

	@Override
	public boolean update(Content content) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE Contents SET title = ?, description = ? WHERE id = ?",
							  content.getTitle(),
							  content.getDescription(),
							  content.getId());
		
		for(Tag tag : content.getTags()) {
			DaoUtil.executeUpdate(connection, "INSERT INTO ContentTags(contents_id, tags_id) VALUES(?, ?)", content.getId(), tag.getId());
		}
		
		return true;
	}

	@Override
	public boolean delete(Content content) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM Contents WHERE id = ?", content.getId());
		
		return true;
	}	
}
