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
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author daca97002
 */
//GETRECIPE WILL BE USED OFTEN, Needs to be effective.
//ADDRECIPE IS not as prioriticed
@Stateless
public class RecipeBean {

    public boolean addRecipe(String body) {
        List<ReIngredient> list = new ArrayList<ReIngredient>();
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

        int temp;
        while (ings.contains(" ")) {
            ReIngredient re = new ReIngredient();
            System.out.println(ings);
            temp = ings.indexOf(" ");
            if (ings.charAt(0) != '-') {
                re.setIngName(ings.substring(0, temp));
            } else {
                re.setIngName(ings.substring(1, temp));
            }
            ings = ings.substring(temp + 1);
            re.setIngAmount(ings.substring(0, ings.indexOf("-")));
            ings = ings.substring(ings.indexOf("-"));
            list.add(re);
            System.out.println(list);
        }
        int test = 0;

        //save ingredients, than add recipe, save recipe id then add rec-ing
        int test2 = 0;
        for (ReIngredient rI : list) {
            String rITemp = rI.getIngName();
            System.out.println(test + rITemp + ":" + rI.getIngAmount());
            test++;
            ResultSet rs;

            try {
                System.out.println("database test " + test2);
                test2++;
                Connection connection = ConnectionFactory.make("testserver");
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO ingredients VALUES (NULL, ?, NULL)");
                stmt.setString(1, rI.getIngName());
                System.out.println(stmt);
                try {
                    stmt.execute();
                    stmt.close();
                    stmt = connection.prepareStatement("SELECT LAST_INSERT_ID() AS id;");
                    rs = stmt.executeQuery();
                    rI.setIngName(rs.getString("id"));
                    System.out.println("RS id " + rI.getIngName());
                    connection.close();
                    
                } catch (MySQLIntegrityConstraintViolationException e) {
                    System.out.println("ING ALREADY EXISTS " + e.getMessage());
                    try {
                        stmt.close();
                        stmt = connection.prepareStatement("SELECT id FROM ingredients "
                                + "WHERE name = ?");
                        stmt.setString(1, rITemp);
                        System.out.println("So far so good "+stmt);
                        rs = stmt.executeQuery();
                        
                        rI.setIngName(rs.getString("id"));
                        System.out.println("Id from database "+rI.getIngName());
                    } catch (Exception f) {
                        System.out.println("Errorrrrs " + f.getMessage());
                    }

                }

                //stmt.setString(2, rI.getIngName());
            } catch (Exception e) {
                System.out.println("errorrr: " + e);
                return false;
            }
        }
        return true;
    }

}
