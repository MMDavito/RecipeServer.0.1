/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import nu.te4.objects.Ingredient;
import nu.te4.objects.Recipe;
import nu.te4.support.User;

/**
 *
 * @author daca97002
 */
//GETRECIPE WILL BE USED OFTEN, Needs to be effective.
//ADDRECIPE IS not as prioriticed
@Stateless
public class RecipeBean {

    public boolean addRecipe(String body, HttpHeaders httpHeaders) {
        int recId = Recipe.addRecipe(body);
        if (recId < 0) {
            return false;
        }
        return User.addAuthor(recId, httpHeaders);
    }

    public JsonArray getIngs() {
        return Ingredient.getAllIngs();
    }

    public boolean addIng(String body) {
        return Ingredient.addIng(body);
    }

    public JsonArray getRecipes() {
        return Recipe.getRecipes();
    }

    public JsonObject getRecipe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public JsonArray getRecipeIngs(int id) {
        return Recipe.getIngsForRecipe(id);
    }
}
