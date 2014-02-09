package ba.etf.instavine.resources.responses;

import javax.ws.rs.WebApplicationException;

public class BadRequestException extends WebApplicationException 
{	
	private static int HttpStatusCode = 400;
	
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message)
	{
        super((javax.ws.rs.core.Response) Response.error(HttpStatusCode, message));
    }
}
