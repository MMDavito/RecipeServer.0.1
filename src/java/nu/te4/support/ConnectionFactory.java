/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author daca97002
 */
public class ConnectionFactory {

    public static Connection make(String server) throws Exception {
        System.out.println("Your server is: "+server);
        if (server.equals("testserver")) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1/project4"; //"jdbc:mysql://10.16.58.152/sport"
            String user = "root";
            String password = "";

            Connection connection = (Connection) DriverManager.getConnection(url, user, password);
            System.out.println("Kommer jag hit");
            return connection;
        }
        return null;
    }
}
