/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author daca97002
 */
public class Category {

    public static JsonArray getAllCats() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "SELECT * FROM `recipe_cat`";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("cat_id", id)
                        .add("cat_name", name).build());
            }
            conn.close();
            return jsonArrayBuilder.build();
        } catch (Exception e) {
            System.err.println("cats: " + e);
        }
        return null;
    }

    public static int addCat(String body) {

        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        String name = data.getString("name");
        if (name.length() > 30) {
            return -1;
        }
        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "INSERT INTO `recipe_cat` VALUES(NULL,?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.execute();
            stmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.err.println("Category " + e);
        }
        return -2;
    }
}
