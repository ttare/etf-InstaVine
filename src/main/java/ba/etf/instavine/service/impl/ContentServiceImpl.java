package ba.etf.instavine.service.impl;

import java.util.List;

import ba.etf.instavine.dao.ContentDao;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.TagDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.Tag;
import ba.etf.instavine.service.ContentService;

public class ContentServiceImpl implements ContentService
{
	private DaoFactory daoFactory;
	private ContentDao contentDao;
	private TagDao tagDao;	
	
	public ContentServiceImpl() 
	{
		daoFactory = JDBCDaoFactory.getInstance();
		
	    contentDao = daoFactory.getContentDao();
	    tagDao = daoFactory.getTagDao();
	}
	
	@Override
	public List<Content> findAll(int offset, int limit) {
		return fillWithTags(contentDao.findAll(offset, limit));
	}

	@Override
	public List<Content> findRecentlyAdded(int offset, int limit) {
		return fillWithTags(contentDao.findRecentlyAdded(offset, limit));
	}
	
	@Override
	public List<Content> findBestRated(int offset, int limit) {
		return fillWithTags(contentDao.findBestRated(offset, limit));
	}
	
	@Override
	public List<Content> findAllByFilter(String filter, int offset, int limit) {
		return fillWithTags(contentDao.findAllByFilter(filter, offset, limit));
	}

	@Override
	public Content find(int id) 
	{
		Content content = contentDao.find(id);
		if (content == null) {
			return content;
		}
		
		fillWithTag(content);
		
		return content;
	}

	@Override
	public void insert(Content content) {
		contentDao.insert(content);
	}

	@Override
	public void update(Content content) 
	{
		tagDao.deleteByContent(content.getId());
		contentDao.update(content);		
	}

	@Override
	public void delete(Content content) 
	{
		tagDao.deleteByContent(content.getId());
		contentDao.delete(content);
	}

	@Override
	public List<Tag> findAllTags(int offset, int limit) {
		return tagDao.findAll(offset, limit);
	}

	@Override
	public Tag findTag(int tagId) {
		return tagDao.find(tagId);
	}

	@Override
	public void insertTag(Tag tag) {
		tagDao.insert(tag);
	}

	@Override
	public void deleteTag(Tag tag) {
		tagDao.delete(tag);
	}

	private Content fillWithTag(Content content) {
		content.setTags(tagDao.findByContent(content.getId()));
		return content;
	}
	
	private List<Content> fillWithTags(List<Content> contents) {
		for(Content content : contents) {
			fillWithTag(content);
		}
		return contents;
	}
}
