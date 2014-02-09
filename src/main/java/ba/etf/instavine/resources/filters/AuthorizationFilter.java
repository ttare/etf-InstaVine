package ba.etf.instavine.resources.filters;

import ba.etf.instavine.resources.responses.UnauthorizedException;
import ba.etf.instavine.service.LoginService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.impl.ServiceFactoryImpl;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class AuthorizationFilter implements ContainerRequestFilter, ResourceFilter
{
	private static final String DEFAULT_USERNAME = "joe.public";
	
	private ServiceFactory serviceFactory;
	private LoginService loginService;
	
	public AuthorizationFilter()
	{
		serviceFactory = ServiceFactoryImpl.getInstance();
		loginService = serviceFactory.getLoginService();
	}
	
	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) 
	{
		String authToken = getAuthToken(containerRequest);
		
		String username = getUsernameFromAuthToken(authToken), 
			   key = getKeyFromAuthToken(authToken);
		
		if (username != null && key != null) 
		{
			if (!loginService.validateSession(username, key)) {
				throw new UnauthorizedException("Session is invalid.");
			}
		} else {
			username = DEFAULT_USERNAME;
		}
		
		// TODO(kklisura): Remove following comments to enable privileges.
		//if (!securityService.isAllowed(username, resource, privilege)) {
		//	throw new UnauthorizedException("You don't have permission for this resource.");
		//}
		
		return containerRequest;
	}
	
	private String getAuthToken(ContainerRequest containerRequest) 
	{
		String authToken = containerRequest.getHeaderValue("X-Auth");
		if (authToken == null) 
		{
			if (!containerRequest.getCookieNameValueMap().containsKey("auth")) {
				return null;
			}
			
			authToken = containerRequest.getCookieNameValueMap().getFirst("auth");
		}
		
		return authToken;
	}
	
	private static String getUsernameFromAuthToken(String authToken)
	{
		if (authToken == null) {
			return null;
		}
		
		int separatorPosition = authToken.indexOf(':');
		if (separatorPosition == -1) {
			return null;
		}
		
		return authToken.substring(0, separatorPosition);
	}
	
	private static String getKeyFromAuthToken(String authToken)
	{
		if (authToken == null) {
			return null;
		}
		
		int separatorPosition = authToken.indexOf(':');
		if (separatorPosition == -1) {
			return null;
		}
		
		return authToken.substring(separatorPosition + 1);
	}
}
