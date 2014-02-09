package ba.etf.instavine.resources.responses;

import javax.ws.rs.WebApplicationException;

public class ResourceNotFoundException extends WebApplicationException 
{	
	private static int HttpStatusCode = 404;
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) 
	{
        super((javax.ws.rs.core.Response) Response.error(HttpStatusCode, message));
    }
}

