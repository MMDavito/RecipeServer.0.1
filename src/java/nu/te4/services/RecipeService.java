/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.beans.RecipeBean;

/**
 *
 * @author daca97002
 */
@Path("/")
public class RecipeService {

    @EJB
    RecipeBean recipeBean;

    @POST
    @Path("recipe")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRecipe(String body) {
        if (!recipeBean.addRecipe(body)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
