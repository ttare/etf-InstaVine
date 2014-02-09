package ba.etf.instavine.utils;

import javax.ws.rs.core.MultivaluedMap;

public final class ResourceUtil
{
	public static boolean hasAll(MultivaluedMap<String, String> formParams, String... arguments)
	{
		for(int i = 0; i < arguments.length; i++) 
		{
			if (formParams.getFirst(arguments[i]) == null) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public static boolean isInt(String in) 
	{
		if (in == null)
			return false;
		
		try {
			Integer.parseInt(in);
		} catch(NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	
	public static String firstOrDefault(String in, String def) {
		return in == null ? def : in;
	}
	
	
	public static Integer generateSalt()
	{
		return (int)(Math.random()*10000000); 
	}

}
