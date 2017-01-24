/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
}
