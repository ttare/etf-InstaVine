package ba.etf.instavine.resources.responses;

import javax.ws.rs.WebApplicationException;

public class UnauthorizedException extends WebApplicationException 
{	
	private static int HttpStatusCode = 401;
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
        super((javax.ws.rs.core.Response) Response.error(HttpStatusCode, message));
    }
}
