package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.TagDao;
import ba.etf.instavine.dao.mapper.RowMapper;
import ba.etf.instavine.dao.mapper.TagRowMapper;
import ba.etf.instavine.models.Tag;
import ba.etf.instavine.utils.DaoUtil;


public class TagDaoImpl implements TagDao
{
	private DaoFactory daoFactory;
	private static RowMapper rowMapper = new TagRowMapper();
	
	
	public TagDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	
	@Override
	public List<Tag> findAll(int offset, int limit) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, 
									rowMapper, 
									"SELECT * FROM Tags LIMIT ?, ?",
									offset,
									limit);
	}
	
	@Override
	public List<Tag> findByContent(int contentId) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQuery(connection, rowMapper, "SELECT t.* FROM Tags t, ContentTags ct WHERE ct.tags_id = t.id AND ct.contents_id = ?", contentId);
	}

	@Override
	public Tag find(int id) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, 
											rowMapper, 
											"SELECT * FROM Tags WHERE id = ?",
											id);
	}
	
	@Override
	public Tag find(String name) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		return DaoUtil.executeQueryReturnOne(connection, 
											rowMapper, 
											"SELECT * FROM Tags WHERE name = ?",
											name);
	}

	@Override
	public boolean insert(Tag tag) throws DaoException
	{ 
		Connection connection = daoFactory.getConnection();
		
		int rowId = DaoUtil.executeUpdate(connection, 
										  "INSERT INTO Tags (name) VALUES(?)",
										  tag.getName());
		
		tag.setId(rowId);
		
		return true;
	}

	@Override
	public boolean update(Tag tag) throws DaoException
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, 
							  "UPDATE Tags SET name = ? WHERE id = ?",
							  tag.getName(),
							  tag.getId());
		
		return true;
	}

	@Override
	public boolean delete(Tag tag) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM ContentTags WHERE tags_id = ?", tag.getId());
		DaoUtil.executeUpdate(connection, "DELETE FROM Tags WHERE id = ?", tag.getId());
		
		return true;
	}

	@Override
	public boolean deleteByContent(int contentId) throws DaoException 
	{
		Connection connection = daoFactory.getConnection();
		
		DaoUtil.executeUpdate(connection, "DELETE FROM ContentTags WHERE contents_id = ?", contentId);
		
		return true;
	}
	
}
