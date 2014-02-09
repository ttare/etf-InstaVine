package ba.etf.instavine.service;

public interface ServiceFactory 
{
	public UserService getUserService();

	public LoginService getLoginService();
	
	public ContentService getContentService();
}
