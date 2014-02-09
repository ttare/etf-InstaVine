package ba.etf.instavine.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.User;

public abstract class BaseResource 
{
	@QueryParam("offset") @DefaultValue("0")
	protected int offset;
	
	@QueryParam("limit") @DefaultValue("30") 
	protected int limit;
	
	@HeaderParam("X-Auth") @DefaultValue("")
	protected String xAuthHeader;
	
	protected User getCurrentUser() {
		if (xAuthHeader.length() == 0) {
			return null;
		}
		
		int pos = xAuthHeader.indexOf(':');
		
		String username = xAuthHeader.substring(0, pos);
		return JDBCDaoFactory.getInstance().getUserDao().find(username);
	}
}