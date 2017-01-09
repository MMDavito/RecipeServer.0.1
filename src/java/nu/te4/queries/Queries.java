/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.queries;

import com.mysql.jdbc.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.te4.support.ConnectionFactory;
/**
 *
 * @author daca97002
 */
public class Queries {
    
    //FUTURE IMPLEMENTATION
    
    public static int insertIngredient(String name, String info){
        try {
            Connection conn = ConnectionFactory.make("testserver");            
            
        } catch (Exception ex) {
            System.out.println("fel "+ex);
        }
        return -1;
    }
}
