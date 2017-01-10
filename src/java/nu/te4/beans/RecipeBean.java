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
import nu.te4.objects.Ingredient;

/**
 *
 * @author daca97002
 */
//GETRECIPE WILL BE USED OFTEN, Needs to be effective.
//ADDRECIPE IS not as prioriticed
@Stateless
public class RecipeBean {

    public boolean addRecipe(String body) {
        ArrayList<ReIngredient> list = new ArrayList<ReIngredient>();
        //ReIngredient re = new ReIngredient();

        System.out.println(body);

        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        String name = data.getString("name");
        String cat = data.getString("cat");
        String ings = data.getString("ing");
        String inst = data.getString("inst");
        String time = Time.dTi();
        System.out.println("Kommer till efter läsning");
        System.out.println(ings);
        return false;
    }
    public JsonArray getIngs(){
        return Ingredient.getAllIngs();
    }
}