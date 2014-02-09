package ba.etf.instavine.resources.filters;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;

@Provider
public class ResourceFilterFactory extends RolesAllowedResourceFilterFactory 
{	
	public ResourceFilterFactory() {
		
	}
	
    @Override
    public List<ResourceFilter> create(AbstractMethod am) 
    {
        List<ResourceFilter> filters = super.create(am);
        if (filters == null) {
            filters = new ArrayList<ResourceFilter>();
        }
        
        /*if (am.getResource() != null && am.getResource().getPath() != null && am.isAnnotationPresent(Privilege.class)) 
        {
        	String resource = am.getResource().getPath().getValue();
        	
        	registerResource(resource);
        	//securityService.registerPrivilegeType(privilege);
        	
        	filters = new ArrayList<ResourceFilter>(filters);
          	//filters.add(0, new AuthorizationFilter(resource, privilege));
        }*/
        
        return filters;
    }
}
