package ba.etf.instavine.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.User;
import ba.etf.instavine.resources.responses.BadRequestException;
import ba.etf.instavine.service.LoginService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.impl.ServiceFactoryImpl;
import ba.etf.instavine.utils.ResourceUtil;


@Path("login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource 
{
	private static ServiceFactory serviceFactory;
	private static LoginService loginService;
	
	private DaoFactory daoFactory;
	private UserDao userDao;
	
	public LoginResource()
	{
		this.daoFactory = JDBCDaoFactory.getInstance();
		this.userDao = daoFactory.getUserDao();
		
		serviceFactory = ServiceFactoryImpl.getInstance();
		loginService = serviceFactory.getLoginService();
	}
	
	class Auth {
		public String auth;
		public User user;
	};
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Auth doLogin(MultivaluedMap<String, String> formParams)
	{
		if (!ResourceUtil.hasAll(formParams, "username", "password")) {
			throw new BadRequestException("You are missing username and/or password field.");
		}
		
		Auth auth = new Auth();
		
		User user = userDao.find(formParams.getFirst("username"));
		
		if (user != null) 
		{
			String salt = Integer.toString(user.getSalt());
			String plainPassword = formParams.getFirst("password").concat(salt);

			String password = loginService.encryptPassword(plainPassword);

			if (user.getPassword().equals(password)) 
			{
				userDao.logActionLogin(user);
				
				auth.auth = user.getPassword();
				auth.user = user;
			}
		}
		
		return auth;
	}
}
