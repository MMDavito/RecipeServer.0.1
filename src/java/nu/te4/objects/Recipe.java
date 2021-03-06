/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author daca97002
 */
public class Recipe {

    public static int addRecipe(String body) {
        //ReIngredient re = new ReIngredient();

        System.out.println("kroppen är här\n" + body);

        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        System.out.println(data);
        String name = data.getString("name");
        System.out.println("name "+name);
        int cat = data.getInt("cat");
        System.out.println("cat");
        String inf = data.getString("basic-inf");
        String ings = data.getString("ing");
        System.err.println("basic-inf");
        String inst = data.getString("inst");
        String img = data.getString("img-link");
        System.out.println("img-link");
        String time = Time.dTi();
        ArrayList<ReIngredient> reIngs = new ArrayList<>();

        System.out.println("Kommer till efter läsning " + cat);
        System.out.println(ings);
        while (ings.contains(" ")) {
            int space = ings.indexOf(" ");
            int ingId;
            ReIngredient reIng = new ReIngredient();
            if (ings.charAt(0) != '-') {
                ingId = Integer.parseInt(ings.substring(0, space));
            } else {
                ingId = Integer.parseInt(ings.substring(1, space));
            }
            ings = ings.substring(space + 1);
            int slash = ings.indexOf("-");

            String amount = ings.substring(0, slash);
            ings = ings.substring(slash);
            reIng.setIngId(ingId);
            boolean boo = reIng.setIngAmount(amount);
            if (!boo) {
                return -3;
            }
            reIngs.add(reIng);

        }
        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "INSERT INTO recipes VALUES(NULL,?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cat);
            stmt.setString(2, name);
            stmt.setString(3, time);
            stmt.setString(4, inf);
            stmt.setString(5, inst);
            stmt.setString(6, img);
            stmt.execute();
            stmt.close();

            query = "SELECT LAST_INSERT_ID() AS id;";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            stmt.close();
            conn.close();

            if (ReIngredient.addRecIngrs(id, reIngs)) {
                return id;
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        }
        return -1;
    }

    public static JsonArray getRecipes() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        try {
            System.out.println("Trying to get recipes");
            Connection conn = ConnectionFactory.make("testserver");
            String query = "SELECT * FROM `recipe_list` ORDER BY category,name";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String cat = rs.getString("category");
                String name = rs.getString("name");
                String timeRevised = rs.getString("time_revised");
                String info = rs.getString("basic_info");
                String instruction = rs.getString("instruction");
                String imgSrc = rs.getString("image");
                if (info == null) {
                    info = "";
                }
                if (imgSrc == null) {
                    imgSrc = "";
                }
                if (cat == null) {
                    cat = "Category undefined";
                }
                System.out.println("Trying to build recipeJsonArray");
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("cat", cat)
                        .add("name", name)
                        .add("info", info)
                        .add("instruction", instruction)
                        .add("img_src", imgSrc)
                        .add("time_revised", timeRevised).build());
            }
            conn.close();
            return jsonArrayBuilder.build();
        } catch (Exception e) {
            System.err.println("Recipe " + e);
        }
        return null;
    }

    public static JsonObject getRecipe(int id) {
        try {
            System.out.println("Trying to get recipes");
            Connection conn = ConnectionFactory.make("testserver");
            String query = "SELECT * FROM `recipe_list` WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getInt("id");
            String cat = rs.getString("category");
            String name = rs.getString("name");
            String timeRevised = rs.getString("time_revised");
            String info = rs.getString("basic_info");
            String instruction = rs.getString("instruction");
            String imgSrc = rs.getString("image");
            if (info == null) {
                info = "";
            }
            if (imgSrc == null) {
                imgSrc = "";
            }
            if (cat == null) {
                cat = "Category undefined";
            }
            System.out.println("Trying to build recipeJsonArray");
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            JsonObject value = factory.createObjectBuilder()
                    .add("id", id)
                    .add("cat", cat)
                    .add("name", name)
                    .add("info", info)
                    .add("instruction", instruction)
                    .add("img_src", imgSrc)
                    .add("time_revised", timeRevised).build();

            conn.close();
            return value;
        } catch (Exception e) {
            System.err.println("Recipe " + e);
        }
        return null;
    }

    public static JsonArray getIngsForRecipe(int id) {
        JsonObject recipe = getRecipe(id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder jsonArrayBuilder2 = Json.createArrayBuilder();
        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "SELECT * FROM rec_ing WHERE id_rec = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idIng = rs.getInt("id");
                String ingName = rs.getString("ing_name");
                String ingAmount = rs.getString("ing_amount");
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("rec_id", id)
                        .add("id_ing", idIng)
                        .add("name_ing", ingName)
                        .add("amount_ing", ingAmount).build());
            }
            JsonArray ingredients = jsonArrayBuilder.build();
            jsonArrayBuilder2.add(Json.createObjectBuilder()
                    .add("recipe", recipe)
                    .add("ingredients", ingredients).build());

            conn.close();
            return jsonArrayBuilder2.build();
        } catch (Exception e) {
        }
        return null;
    }

}
