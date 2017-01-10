/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import nu.te4.objects.Time;
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
            System.out.println("Fel utavhelvete " + e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param httpHeaders Base64 username:password
     * @return 1 if success, -1 if username exsists in database, 
     * -2 is generall errormessage
     */
    public static int regUser(HttpHeaders httpHeaders) {
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
            if (createUser(username, password)) {
                return 1;
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("User already existead ");
            return -1;
        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
        }
        return -2;
    }

    /**
     * creates user in database.
     * @param username username
     * @param password password
     * @return true if createad, else throws exception.
     * @throws java.sql.SQLException SQL Exceptions.
     * @throws java.lang.Exception if Connection encounters errors.
     */
    public static boolean createUser(String username, String password) throws SQLException, Exception {
        String time = Time.dTi();

        String hashedPW = BCrypt.hashpw(password, BCrypt.gensalt());
        Connection con = ConnectionFactory.make("testserver");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO users VALUES(NULL,?,?,?);");
        stmt.setString(1, username);
        stmt.setString(2, hashedPW);
        stmt.setString(3, time);
        stmt.executeUpdate();
        con.close();
        return true;
    }

    /**
     * Checks if user excists in database
     *
     * @param username username
     * @param password password
     * @return true if authoricatead, else false.
     */
    public static boolean checkUser(String username, String password) {
        try {
            Connection con = ConnectionFactory.make("testserver");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            String hashedPW = rs.getString("password");
            con.close();

            return BCrypt.checkpw(password, hashedPW);
        } catch (Exception e) {
            System.err.println("ERROR " + e.getMessage());
        }
        return false;
    }
}
