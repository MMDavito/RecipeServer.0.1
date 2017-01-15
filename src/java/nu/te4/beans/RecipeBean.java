/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import nu.te4.support.ConnectionFactory;
import nu.te4.objects.ReIngredient;
import nu.te4.objects.Time;
import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
        return User.addAuthor(recId, httpHeaders);
    }

    public JsonArray getIngs() {
        return Ingredient.getAllIngs();
    }

    public boolean addIng(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        String name = data.getString("name");
        String info = data.getString("information");
        Ingredient ing = new Ingredient();
        ing.setName(name);
        ing.setInfo(info);
        int boo;
        boo = Ingredient.addIngredient(ing);
        if (boo > 0) {
            return true;
        }
        return false;
    }
}
