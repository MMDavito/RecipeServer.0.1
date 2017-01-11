/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author daca97002
 */
public class ReIngredient {

    private int ingId;
    private String ingAmount;

    public int getIngId() {
        return ingId;
    }

    public boolean setIngId(int ingId) {
        System.out.println(ingId);
        this.ingId = ingId;
        if (ingId > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getIngAmount() {
        return ingAmount;
    }

    public boolean setIngAmount(String ingAmount) {
        System.out.println(ingAmount);
        if (ingAmount.length() <= 6) {
            this.ingAmount = ingAmount;
            return true;
        } else {
            return false;
        }
    }

    //RETURNS ALL INGREDIENT INFORMATION ASOSIATEAD WITH RECIPE
    public static ArrayList<ReIngredient> getRecIngr(String recipeID) {
        List<ReIngredient> list = new ArrayList<>();

        return (ArrayList<ReIngredient>) list;
    }

    public static boolean addRecIngrs(int recId, ArrayList<ReIngredient> ings) {
        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "INSERT INTO `recipe_ing` VALUES(NULL,?,?,?);";
            for (ReIngredient ing : ings) {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, recId);
                stmt.setInt(2, ing.ingId);
                stmt.setString(3, ing.ingAmount);
                stmt.execute();
                stmt.close();
            }
            return true;
        } catch (Exception e) {
            System.out.println("error "+e);
        }
        return false;
    }
}
