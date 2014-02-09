package ba.etf.instavine.service;

public interface SecurityService 
{
	public boolean isAllowed(String username, String resource, String privilege);
	
	public void registerPrivilegeType(String name);
}
