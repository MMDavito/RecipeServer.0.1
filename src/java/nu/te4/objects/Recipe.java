/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import com.mysql.jdbc.Connection;
import com.sun.org.apache.bcel.internal.generic.Select;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
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
        String name = data.getString("name");
        int cat = data.getInt("cat");
        String inf = data.getString("basic-inf");
        String ings = data.getString("ing");
        String inst = data.getString("inst");
        String img = data.getString("img-link");
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
            reIng.setIngAmount(amount);
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

            if (ReIngredient.addRecIngrs(id, reIngs)) {
                return id;
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        }
        return -1;
    }

}
