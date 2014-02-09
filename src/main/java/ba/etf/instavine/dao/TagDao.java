package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.Tag;

public interface TagDao
{
	public List<Tag> findAll(int offset, int limit) throws DaoException;
	
	public List<Tag> findByContent(int contentId) throws DaoException;
	
	public Tag find(int id) throws DaoException;
	
	public Tag find(String name) throws DaoException;	
	
	public boolean insert(Tag tag) throws DaoException;
	
	public boolean update(Tag tag) throws DaoException;
	
	public boolean delete(Tag tag) throws DaoException;
	
	public boolean deleteByContent(int contentId) throws DaoException;
}
