package ba.etf.instavine.resources.responses;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

public abstract class Response extends javax.ws.rs.core.Response
{
	@SuppressWarnings("unused")
	private static class PaginatedResponse
	{	
		public int offset;
		public int limit;
		public int max;
		
		public Object entity;
		
		public PaginatedResponse() {
			
		}
		
		public PaginatedResponse(Object entity, int offset, int limit, int max) {
			this.entity = entity;
			this.offset = offset;
			this.limit = limit;
			this.max = max;
		}
	}
	
	@SuppressWarnings("unused")
	@JsonPropertyOrder({"code", "message"})
	private static class ErrorResponse 
	{
		public int code;
		public String message;
		
		public ErrorResponse() {
			
		}
		
		public ErrorResponse(int code, String message) {
			this.code = code;
			this.message = message;
		}		
	}
	
	private Response() {
		
	}
	
	public static Object success() 
	{
		return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorResponse(200, "Success."))
				.build();
	}
	
	public static Object entity(Object object) 
	{
		return Response.ok()
				 .header("Access-Control-Allow-Origin", "http://localhost/")
				.type(MediaType.APPLICATION_JSON)
				.entity(object)
				.build();
	}
	
	public static Object paginated(Object object, int offset, int limit, int max) 
	{
		PaginatedResponse paginatedResponse = new PaginatedResponse(object, offset, limit, max);
		
		return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity(paginatedResponse)
				.build();
	}
	
	public static Object error(int statusCode, int errorCode, String message) 
	{
		return Response.status(statusCode)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorResponse(errorCode, message))
				.build();
	}
	
	public static Object error(int code, String message) {
		return Response.error(code, code, message);
	}
	
	public static Object redirect(Object resourceObject, int id) 
	{	
		Path path = (Path) resourceObject.getClass().getAnnotation(Path.class);

		return Response.created(URI.create(path.value() + "/" + id))
				.status(301)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorResponse(201, "Successfully created."))
				.build();
	}
}
