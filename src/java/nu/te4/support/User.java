/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author daca97002
 */
public class User {

    /**
     *
     * @param httpHeaders Base64 username:password 
     * @return true if authoricatead, else false
     */
    public static boolean authoricate(HttpHeaders httpHeaders) {
        try {
            List<String> authHeader = httpHeaders.getRequestHeader(HttpHeaders.AUTHORIZATION);
            String header = authHeader.get(0);
            header = header.substring(header.indexOf(" ") + 1);
            byte[] decoded = Base64.getDecoder().decode(header);
            String userPass = new String(decoded);
            System.out.println(userPass);
            //plocka ut anv och lösenord
            String username = userPass.substring(0, userPass.indexOf(":"));
            String password = userPass.substring(userPass.indexOf(":") + 1);
            
            if (checkUser(username, password)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Fel utavhelvete "+e.getMessage());
        }
        return false;
    }

    /**
     * creates user in database.
     * @param username username
     * @param password password
     * @return true if createad, else false
     */
    public static boolean createUser(String username, String password) {
        String hashedPW = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            Connection con = ConnectionFactory.make("testserver");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO users VALUES(?,?);");
            stmt.setString(1, username);
            stmt.setString(2, hashedPW);
            stmt.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR "+e.getMessage());
        }
        return false;
    }

    /**
     * Checks if user excists in database
     * @param username username
     * @param password password
     * @return true if authoricatead, else false.
     */
    public static boolean checkUser(String username, String password){
        try {
            Connection con = ConnectionFactory.make("testserver");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            String hashedPW =rs.getString("password");
            con.close();
                            
                return BCrypt.checkpw(password, hashedPW);
            
        } catch (Exception e) {
            System.err.println("ERROR "+e.getMessage());
        }
        return false;
    }
}
