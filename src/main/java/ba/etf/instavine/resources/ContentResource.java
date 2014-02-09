package ba.etf.instavine.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import ba.etf.instavine.dao.TagDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.Content;
import ba.etf.instavine.models.Tag;
import ba.etf.instavine.resources.responses.BadRequestException;
import ba.etf.instavine.resources.responses.ResourceNotFoundException;
import ba.etf.instavine.resources.responses.Response;
import ba.etf.instavine.service.ContentService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.impl.ServiceFactoryImpl;
import ba.etf.instavine.utils.ResourceUtil;

@Path("contents")
@Produces(MediaType.APPLICATION_JSON)
public class ContentResource extends BaseResource
{
	private ServiceFactory serviceFactory;
	private ContentService contentService;
	private TagDao tagDao;
	
	
	public ContentResource()
	{
		serviceFactory = ServiceFactoryImpl.getInstance(); 
		contentService = serviceFactory.getContentService();
		tagDao = JDBCDaoFactory.getInstance().getTagDao();
	}
	
	@GET
	public Object getAllContents(@QueryParam("filter") @DefaultValue("") String filter) 
	{
		if (filter.length() == 0) {
			return Response.paginated(contentService.findAll(offset, limit), offset, limit, -1);
		}
		
		switch(filter)
		{
		case "newest":
			return Response.paginated(contentService.findRecentlyAdded(offset, limit), offset, limit, -1);
		case "rated":
			return Response.paginated(contentService.findBestRated(offset, limit), offset, limit, -1);
		}
		
		return Response.paginated(contentService.findAllByFilter(filter, offset, limit), offset, limit, -1);
	}
	
	@GET
	@Path("{id}")
	public Object getContent(@PathParam("id") int id) 
	{
		Content content = contentService.find(id);
		if (content == null) {
			throw new ResourceNotFoundException("Content not found");
		}
		
		return Response.entity(content);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Object createNewContent(MultivaluedMap<String, String> formParams) 
	{
		if (!ResourceUtil.hasAll(formParams, "title") || 
			!ResourceUtil.isInt(formParams.getFirst("year")) ||
			!ResourceUtil.isInt(formParams.getFirst("length")))
		{
			throw new BadRequestException("You are missing some fields.");
		}
		
		Content content = new Content();
		
		content.setTitle(formParams.getFirst("title"));
		//content.setYear(Integer.parseInt(formParams.getFirst("year")));
		//content.setLength(Integer.parseInt(formParams.getFirst("length")));
		
		if (formParams.getFirst("actors") != null) {
			//content.setActors(formParams.getFirst("actors"));
		}
		if (formParams.getFirst("director") != null) {
			//content.setDirector(formParams.getFirst("director"));
		}
		if (formParams.getFirst("description") != null) {
			//content.setDescription(formParams.getFirst("description"));
		}
		
		String tagName = null;
		int i = 0;
		while((tagName = formParams.getFirst("tags[" + i + "][name]")) != null) {
			Tag tag = tagDao.find(tagName);
			content.addTag(tag);
			i++;
		}
		
		contentService.insert(content);
		
		return Response.redirect(this, content.getId());
	}
	
	@POST
	@Path("{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Object updateContent(@PathParam("id") int id, MultivaluedMap<String, String> formParams) 
	{	
		Content content = contentService.find(id);
		if (content == null) {
			throw new ResourceNotFoundException("Content not found.");
		}
		
		if (formParams.getFirst("title") != null) {
			content.setTitle(formParams.getFirst("title"));
		}
		if (formParams.getFirst("actors") != null) {
			//content.setActors(formParams.getFirst("actors"));
		}
		if (formParams.getFirst("director") != null) {
			//content.setDirector(formParams.getFirst("director"));
		}
		if (formParams.getFirst("description") != null) {
			//content.setDescription(formParams.getFirst("description"));
		}
		if (formParams.getFirst("year") != null) {
			//content.setYear(Integer.parseInt(formParams.getFirst("year")));
		}
		if (formParams.getFirst("length") != null) {
			//content.setLength(Integer.parseInt(formParams.getFirst("length")));
		}

		content.getTags().clear();
		
		String tagName = null;
		int i = 0;
		while((tagName = formParams.getFirst("tags[" + i + "][name]")) != null) {
			Tag tag = tagDao.find(tagName);
			content.addTag(tag);
			i++;
		}
		
		contentService.update(content);
		
		return Response.success();	
	}
		
	@DELETE
	@Path("{id}")
	public Object deleteContent(@PathParam("id") int id) 
	{
		Content content = contentService.find(id);
		if (content == null) {
			throw new ResourceNotFoundException("Content not found.");
		}
		
		contentService.delete(content);
		
		return Response.success();
	}
}
