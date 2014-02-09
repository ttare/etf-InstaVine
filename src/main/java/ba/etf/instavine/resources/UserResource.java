package ba.etf.instavine.resources;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import ba.etf.instavine.dao.UserActionDao;
import ba.etf.instavine.dao.UserActionTypeDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.User;
import ba.etf.instavine.models.UserAction;
import ba.etf.instavine.resources.responses.BadRequestException;
import ba.etf.instavine.resources.responses.ResourceNotFoundException;
import ba.etf.instavine.resources.responses.Response;
import ba.etf.instavine.service.LoginService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.UserService;
import ba.etf.instavine.service.impl.ServiceFactoryImpl;
import ba.etf.instavine.utils.DaoUtil;
import ba.etf.instavine.utils.ResourceUtil;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends BaseResource
{
	private ServiceFactory serviceFactory;
	private LoginService loginService;
	private UserService userService;
	private UserActionDao userActionDao;
	private UserActionTypeDao userActionTypeDao;
	
	public UserResource()
	{
		serviceFactory = ServiceFactoryImpl.getInstance();
		userService = serviceFactory.getUserService();
		loginService = serviceFactory.getLoginService();
		userActionDao = JDBCDaoFactory.getInstance().getUserActionDao();
		userActionTypeDao = JDBCDaoFactory.getInstance().getUserActionTypeDao();
	}
	
	@GET
	public Object getAllUsers() {
		return Response.paginated(userService.findAll(offset, limit), 
								  offset, 
								  limit, 
								  userService.numberOfUsers());
	}
	
	@GET
	@Path("{id}")
	public Object getUser(@PathParam("id") int id) 
	{	
		User user = userService.find(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		
		return Response.entity(user);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Object createNewUser(MultivaluedMap<String, String> formParams) 
	{
		if (!ResourceUtil.hasAll(formParams, "password", "lastName", "firstName", "phone", "username", "email", "state[id]", "dateOfBirth"))
		{
			throw new BadRequestException("You are missing some fields.");
		}
		
		Integer salt = ResourceUtil.generateSalt();
		
		String plainPassword = formParams.getFirst("password").concat(salt.toString());
		String password = loginService.encryptPassword(plainPassword);
		
		User user = new User();
	
		user.setUsername(formParams.getFirst("username"));
		user.setEmail(formParams.getFirst("email"));
		user.setPassword(password);
		user.setSalt(salt);
		
		try 
		{
			Date dateOfBirth = DaoUtil.string2Date(formParams.getFirst("dateOfBirth"));
			user.setDateOfBirth(DaoUtil.utilDate2SqlDatw(dateOfBirth));
		} catch (ParseException e) {
			throw new BadRequestException("Bad date format. Date should be in dd-MM-yyyy format.");
		}
		
		userService.insert(user);
		
		return Response.redirect(this, user.getId());
	}
	
	@POST
	@Path("{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Object updateUser(@PathParam("id") int id, MultivaluedMap<String, String> formParams) 
	{
		User user = userService.find(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		
		if (formParams.getFirst("email") != null)
			user.setEmail(formParams.getFirst("email"));

		if (formParams.getFirst("password") != null)
		{
			Integer salt = ResourceUtil.generateSalt();
			
			String plainPassword = formParams.getFirst("password").concat(salt.toString());
			String password = loginService.encryptPassword(plainPassword);
			
			user.setPassword(password);
			user.setSalt(salt);
		}

		if (formParams.getFirst("dateOfBirth") != null)
		{
			try 
			{
				Date dateOfBirth = DaoUtil.string2Date(formParams.getFirst("dateOfBirth"));
				user.setDateOfBirth(DaoUtil.utilDate2SqlDatw(dateOfBirth));
			} catch (ParseException e) {
				throw new BadRequestException("Bad date format. Date should be in yyyy-MM-dd format.");
			}
		}
		
		userService.update(user);

		return Response.success();
	}
	
	@DELETE
	@Path("{id}")
	public Object deleteUser(@PathParam("id") int id) 
	{
		User user = userService.find(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		
		userService.delete(user);
		
		return Response.success();
	}
	
	@GET
	@Path("{id}/history")
	public Object getUserHistory(@PathParam("id") int id) {
		List<UserAction> uac = userActionDao.findAllByUser(id, 0, 999999);
		for(UserAction ua : uac) {
			ua.setUserActionType(userActionTypeDao.find(ua.getUserActionType().getId()));
		}
		return Response.entity(uac);
	}
} 
