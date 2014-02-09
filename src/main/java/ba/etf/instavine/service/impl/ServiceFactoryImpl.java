package ba.etf.instavine.service.impl;

import ba.etf.instavine.service.ContentService;
import ba.etf.instavine.service.LoginService;
import ba.etf.instavine.service.ServiceFactory;
import ba.etf.instavine.service.UserService;

public class ServiceFactoryImpl implements ServiceFactory 
{
	private static ServiceFactoryImpl instance = null;
	
	private UserService userService;
	private LoginService loginService;
	private ContentService contentService;	
	
	private ServiceFactoryImpl() 
	{
		userService = new UserServiceImpl();
		loginService = new LoginServiceImpl();
		contentService = new ContentServiceImpl();
	}
	
	public static ServiceFactory getInstance() {
		if (instance == null) {
			instance = new ServiceFactoryImpl();
		}
		return instance;
	}

	
	@Override
	public UserService getUserService() {
		return userService;
	}

	@Override
	public LoginService getLoginService() {
		return loginService;
	}

	@Override
	public ContentService getContentService() {
		return contentService;
	}	
}
