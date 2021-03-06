/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.beans.RecipeBean;
import nu.te4.support.User;

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
    public Response addRecipe(String body, @Context HttpHeaders httpHeaders) {
        if (User.authoricate(httpHeaders) < 0) {
            return Response.status(401).build();
        }
        if (!recipeBean.addRecipe(body, httpHeaders)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("recipe/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@PathParam("id") int id) {
        System.out.println("trying to get recipe of id " + id);
        JsonArray data = recipeBean.getRecipe(id);
        if (data == null) {
            return Response.serverError().build();
        }
        return Response.ok(data).build();
    }

    @GET
    @Path("recipe_ings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeIngs(@PathParam("id") int id) {
        JsonArray data = recipeBean.getRecipeIngs(id);
        if (data == null) {
            return Response.serverError().build();
        }
        return Response.ok(data).build();
    }

    @DELETE
    @Path("recipe/{id}")
    public Response deleteRecipe(@PathParam("id") int id) {
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

    @GET
    @Path("recipes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes() {
        JsonArray data = recipeBean.getRecipes();
        if (data == null) {
            return Response.serverError().build();
        }
        return Response.ok(data).build();
    }

    @POST
    @Path("ingredient")
    public Response addIng(String body, @Context HttpHeaders httpHeaders) {
        System.out.println(body);
        if (User.authoricate(httpHeaders) < 0) {
            return Response.status(401).build();
        }
            int check = recipeBean.addIng(body);
            if (check == -3) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (check == -1) {
                return Response.status(409).build();
            }
            return Response.status(Response.Status.CREATED).build();
        }    

    @GET
    @Path("ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngs() {
        JsonArray data = recipeBean.getIngs();
        if (data == null) {
            return Response.serverError().build();
        }
        return Response.ok(data).build();
    }
    @GET
    @Path("categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCats() {
        JsonArray data = recipeBean.getCats();
        if (data == null) {
            return Response.serverError().build();
        }
        return Response.ok(data).build();
    }
    @POST
    @Path("category")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCat(String body, @Context HttpHeaders httpHeaders) {
        System.out.println(body);
        if (User.authoricate(httpHeaders) < 0) {
            return Response.status(401).build();
        }
            int check = recipeBean.addCat(body);
            if (check == -2) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (check == -1) {
                return Response.serverError().build();
            }
            //Should I use if(==1) ? OR should i keept this to not miss general errors?
            return Response.status(Response.Status.CREATED).build();
        }
}