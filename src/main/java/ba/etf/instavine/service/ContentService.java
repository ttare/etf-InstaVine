package ba.etf.instavine.service;

import java.util.List;

import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.Tag;

public interface ContentService 
{
    public List<Content> findAll(int offset, int limit);

    public List<Content> findRecentlyAdded(int offset, int limit);

    public List<Content> findBestRated(int offset, int limit);

    public List<Content> findAllByFilter(String filter, int offset, int limit);
    
	public List<Tag> findAllTags(int offset, int limit);
	
	public Tag findTag(int tagId);
	 
	public Content find(int id);
	
	public void insert(Content content);

	public void insertTag(Tag tag);
	 
	public void update(Content content);
	 
	public void delete(Content content);
	
	public void deleteTag(Tag tag);
}
