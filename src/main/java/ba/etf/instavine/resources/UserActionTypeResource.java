package ba.etf.instavine.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserActionTypeDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.UserActionType;


@Path("useractiontypes")
@Produces(MediaType.APPLICATION_JSON)
public class UserActionTypeResource extends BaseResource
{
	private DaoFactory daoFactory;
	private UserActionTypeDao userActionTypeDao;
	
	
	public UserActionTypeResource()
	{
		this.daoFactory = JDBCDaoFactory.getInstance();
		this.userActionTypeDao = daoFactory.getUserActionTypeDao();
	}
	
	
	@GET
	public List<UserActionType> getAllUserActionTypes() 
	{
		return userActionTypeDao.findAll(offset, limit);
	}
	
	@GET
	@Path("{id}")
	public UserActionType getUserActionType(@PathParam("id") int id) 
	{
		return userActionTypeDao.find(id);
	}
	
	@POST
	public void createNewUserActionType() 
	{
		
	}
	
	@DELETE
	@Path("{id}")
	public void deleteUserActionType(@PathParam("id") int id) 
	{
		
	}

} 
