/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static int addIngredient(String name, String info) {
        int ret;

        try {
            Connection conn = ConnectionFactory.make("testserver");
            String query = "INSERT INTO ingredients VALUES (NULL,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, info);
            stmt.execute();
            stmt.close();
            query = "SELECT LAST_INSERT_ID() AS id;";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("id");
                return ret;
            }
        } catch (Exception e) {
            System.out.println("Ingredient " + e);
            return -1;

        }

    }
