/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author daca97002
 */
public class Time {
    public static String dTi(){
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
LocalDateTime now = LocalDateTime.now();
System.out.println(dtf.format(now));
return dtf.format(now);
}
}
