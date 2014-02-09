package ba.etf.instavine.dao;

import java.util.List;

import ba.etf.instavine.models.Content;

public interface ContentDao 
{
	public List<Content> findAll(int offset, int limit) throws DaoException;
	
	public List<Content> findAllByFilter(String filter, int offset, int limit) throws DaoException;
	
	public List<Content> findRecentlyAdded(int offset, int limit) throws DaoException;	
	
	public List<Content> findBestRated(int offset, int limit) throws DaoException;
	
	public List<Content> findTop(int offset, int limit) throws DaoException;
	
	public Content find(int id) throws DaoException;
	
	public boolean insert(Content content) throws DaoException;
	
	public boolean update(Content content) throws DaoException;
	
	public boolean delete(Content content) throws DaoException;
}
