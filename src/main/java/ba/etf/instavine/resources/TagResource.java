package ba.etf.instavine.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import ba.etf.instavine.models.Tag;
import ba.etf.instavine.resources.responses.BadRequestException;
import ba.etf.instavine.resources.responses.ResourceNotFoundException;
import ba.etf.instavine.resources.responses.Response;
import ba.etf.instavine.service.ContentService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.impl.ServiceFactoryImpl;
import ba.etf.instavine.utils.ResourceUtil;

@Path("tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagResource extends BaseResource
{
	private ServiceFactory serviceFactory;
	private ContentService contentService;
	
	public TagResource()
	{
		serviceFactory = ServiceFactoryImpl.getInstance();
		contentService = serviceFactory.getContentService();
	}
	
	@GET
	public Object getTags() {
		return Response.entity(contentService.findAllTags(offset, limit));
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Object createTag(MultivaluedMap<String, String> formParams) 
	{
		if (!ResourceUtil.hasAll(formParams, "name")) {
			throw new BadRequestException("You are missing name field.");
		}
		
		Tag tag = new Tag();
		
		tag.setName(formParams.getFirst("name"));
		
		contentService.insertTag(tag);
		
		return Response.redirect(this, tag.getId());
	}
	
	@DELETE
	@Path("{id}")
	public Object deleteState(@PathParam("id") int id) 
	{
		Tag tag = contentService.findTag(id);
		if (tag == null) {
			throw new ResourceNotFoundException("Tag not found.");
		}
		
		contentService.deleteTag(tag);
		
		return Response.success();
	}
	
} 