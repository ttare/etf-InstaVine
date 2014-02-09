package ba.etf.instavine.models;

import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"tags"})
public class Content 
{
	private int id;
	
	private String title, description;
	private Date length;
    private User user;
	private Map<Integer, Tag> tags;
    private FilterType filterType;
	
	private Date updatedAt, createdAt;
	
	public Content() {
		tags = new HashMap<Integer, Tag>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getLength() {
		return length;
	}

	public void setLength(Date length) {
		this.length = length;
	}
	
	public Collection<Tag> getTags() {
		return tags.values();
	}
	
	public void setTags(List<Tag> tags) {
		for(Tag tag : tags) {
            addTag(tag);
        }
	}

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
	
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
    public void clearTags() {
        tags.clear();
    }

	public void addTag(Tag tag) {
		tags.put(tag.getId(), tag);
	}

    public void removeTag(Tag tag) {
        tags.remove(tag.getId());
    }

    public boolean hasTag(Tag tag) {
        return tags.containsKey(tag.getId());
    }

    public int numberOfTags() {
        return tags.size();
    }
}
