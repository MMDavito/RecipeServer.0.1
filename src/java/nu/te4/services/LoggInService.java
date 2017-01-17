/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.support.User;

/**
 *
 * @author daca97002
 */
@Path("/")
public class LoggInService {

    /**
     *
     * @param httpHeaders Base64 username:password
     * @return http-status 401 if not authoricatead http-status 200: ok if
     * Authoricatead.
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkLogin(@Context HttpHeaders httpHeaders) {
        int login = User.authoricate(httpHeaders);

        System.out.println("integer" + login);
        if (login < 0) {
            return Response.status(401).build();
        }
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("access_level", login)
                .build();
        System.out.println("JSONen "+value);
        return Response.ok(value).build();
    }

    //409 is for duplicate
    @POST
    @Path("register")
    public Response regUser(@Context HttpHeaders httpHeaders) {
        if (User.regUser(httpHeaders) == 1) {
            return Response.status(Response.Status.CREATED).build();

        } else if (User.regUser(httpHeaders) == -1) {
            return Response.status(409).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
