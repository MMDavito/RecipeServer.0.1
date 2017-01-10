/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author daca97002
 */
public class Ingredient {

    private int id;
    private String name;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.length() > 50) {
            return false;
        } else {
            this.name = name;
            return true;
        }
    }

    public String getInfo() {
        return info;
    }

    public boolean setInfo(String info) {
        if (info.length() > 100) {
            return false;
        } else {
            this.info = info;
            return true;
        }
    }

    /**
     * Inserts ingredient into database Returns id
     *
     * @param ing ingredient object wished to add
     * @return null if fail, else the newly createad object with its new id.
     */
    public static Ingredient addIngredient(Ingredient ing) {
        int ret;

        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "INSERT INTO ingredients VALUES (NULL,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ing.getName());
            stmt.setString(2, ing.getInfo());
            stmt.execute();
            stmt.close();
            query = "SELECT LAST_INSERT_ID() AS id;";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("id");
                ing.setId(ret);
                return ing;
            }
        } catch (Exception e) {
            System.out.println("Ingredient " + e);
        }
        return null;
    }

    /**
     *
     * @param ing Ingredient object includeing id, name and (if wished) info
     * @return null if fail, else ing object
     */
    public static Ingredient updateIng(Ingredient ing) {
        if (ing != null) {
            try {
                Connection conn = ConnectionFactory.make("testserver");
                String query = "UPDATE ingredients "
                        + "SET name = ?, information = ? "
                        + "WHERE id = ?;";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, ing.getName());
                stmt.setString(2, ing.getInfo());
                stmt.setInt(3, ing.getId());
                stmt.execute();
                conn.close();
                return ing;

            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        }
        return null;
    }

    public static boolean deleteIng(int ingId) {
        if (ingId > 0) {
            try {
                Connection conn = ConnectionFactory.make("testserver");
                String query = "DELETE FROM ingredients WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, ingId);
                stmt.execute();
                conn.close();
                return true;

            } catch (Exception e) {
            }
        }
        return false;
    }

    public static JsonArray getAllIngs() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        try {
            System.out.println("Got to getIngs");
            Connection conn = ConnectionFactory.make("testserver");
            String query = "SELECT * FROM ingredients ORDER BY name;";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String info = rs.getString("information");
                if (info == null) {
                    info = "";
                }
                System.out.println(id + " :: " + name + " : " + info);
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("info", info).build());
            }

            conn.close();
            return jsonArrayBuilder.build();

        } catch (Exception ex) {
            System.out.println("err: " + ex);
        }

        return null;
    }
}
