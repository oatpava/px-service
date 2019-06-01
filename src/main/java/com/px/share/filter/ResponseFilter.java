package com.px.share.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

public class ResponseFilter implements ContainerResponseFilter {
    private static final Logger LOG = Logger.getLogger(ResponseFilter.class);
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();		
        headers.add("Access-Control-Allow-Origin", "*"); 
        //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");			
        //headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, "+HTTPHeaderNames.AUTH_TOKEN);
        //X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept
        
        headers.add("Access-Control-Expose-Headers", HTTPHeaderNames.AUTH_TOKEN);
        headers.add("Access-Control-Allow-Credentials", "true" );
        headers.add("Access-Control-Max-Age", "300"); //5 min
        
        String path = requestContext.getUriInfo().getPath();
        if ( !path.startsWith( "v1/users/login" ) && !path.startsWith( "swagger.json" ) ) {            
            String token = requestContext.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );
            headers.add(HTTPHeaderNames.AUTH_TOKEN, token);
        }             
    }
}

