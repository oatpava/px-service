package com.px.share.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.px.share.util.Common;
import com.px.share.util.PxInit;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

/**
 *
 *
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class RequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(RequestFilter.class);

    @Context
    HttpServletRequest httpServletRequest;

    @Context
    HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //        LOG.debug("RequestFilter...");

        String client_ip = httpServletRequest.getHeader("x-real-ip");
        if (client_ip == null || client_ip.isEmpty()) { // extract from forward ips
            String ipForwarded = httpServletRequest.getHeader("x-forwarded-for");
            String[] ips = ipForwarded == null ? null : ipForwarded.split(",");
            client_ip = (ips == null || ips.length == 0) ? null : ips[0];
            // extract from remote addr
            client_ip = (client_ip == null || client_ip.isEmpty()) ? httpServletRequest.getRemoteAddr() : client_ip;
        }

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
            requestContext.abortWith(Response.status(Response.Status.OK).build());
            return;
        }

        String path = requestContext.getUriInfo().getPath();
        String token = requestContext.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        LOG.debug("path = "+path);
        LOG.debug("token = "+token);
//        LOG.debug("client_ip = "+client_ip);

        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        MultivaluedMap<String, String> queryparams = requestContext.getUriInfo().getQueryParameters();
        String queryUri = requestContext.getUriInfo().getBaseUriBuilder().toString()+path;
        String queryParam = "";
        for( Map.Entry<String, List<String>> query : queryparams.entrySet() ){
//            LOG.debug("query = "+query);
            String key = query.getKey();
//            LOG.debug("key = "+key);
            if(key.equalsIgnoreCase("q")){
                queryParam = query.getValue().get(0);
//                LOG.debug("queryParam = "+queryParam);
                queryParam = Common.decryptQueryParam(queryParam);
//                LOG.debug("queryParam = "+queryParam);
                queryUri = queryUri+"?"+queryParam;
//                LOG.debug("queryUri = "+queryUri);
            }
        }

        if (!path.startsWith("v1/users/login")
                && !path.startsWith("v1/users/checkEmail")
                && !path.startsWith("v1/users/changePass")
                && !path.startsWith("v1/users/sendEmail")
                && !path.startsWith("swagger.json")                 
                && !path.startsWith("v1/users/getMocktoken")) {
            if (token != null) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(PxInit.KEY);
                    JWTVerifier verifier = JWT.require(algorithm)
                            .withIssuer(PxInit.ISSUER)
                            .build(); //Reusable verifier instance
                    DecodedJWT jwt = verifier.verify(token);
                    String jwtToken = jwt.getToken();
                    jwt = JWT.decode(jwtToken);
                    String userId = jwt.getClaim("pfid").asString();
                    String userType = jwt.getClaim("pftyp").asString();
                    userId = Common.decryptString(userId);
                    userType = Common.decryptString(userType);
//                    headers.add("userID", userId);
//                    headers.add("userType", userType);
//                    headers.add("clientIp", client_ip);
//                    requestContext.setRequestUri(UriBuilder.fromUri(queryUri).build());
                    if(!path.startsWith("checkAuth")){
                        headers.add("userID", userId);
                        headers.add("userType", userType);
                        headers.add("clientIp", client_ip);
                        requestContext.setRequestUri(UriBuilder.fromUri(queryUri).build());
                    }else{
                        if(!userType.equals("1")){
                            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                        }else{
                            requestContext.abortWith(Response.status(Response.Status.OK).build());
                        }
                    }
//                    Algorithm algorithm = Algorithm.HMAC256(PxInit.KEY);
//                    JWTVerifier verifier = JWT.require(algorithm)
//                            .withIssuer(PxInit.ISSUER)
//                            .build(); //Reusable verifier instance
//                    DecodedJWT jwt = verifier.verify(token);
//                    String jwtToken = jwt.getToken();
//                    
//                    jwt = JWT.decode(jwtToken);                    
//                    String userId = jwt.getClaim("pfid").asInt().toString();
//                    String userType = jwt.getClaim("pftyp").asInt().toString();
//                    
//                    headers.add("userID", userId);
//                    headers.add("userType", userType);
//                    headers.add("clientIp", client_ip);
                    
                } catch (JWTVerificationException ex) {
                    //UTF-8 encoding not supported
                    LOG.error(ex);
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } else {
                String apiKey = queryparams.getFirst("api_key");
                if (apiKey.equals("praXis")) {
                    headers.add("userID", "1");
                    headers.add("userType", "2");
                    headers.add("clientIp", client_ip);
                } else {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }
        } else {
            headers.add("userID", "0");
            headers.add("userType", "0");
            headers.add("clientIp", client_ip);
        }

    }
}
