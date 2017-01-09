/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daca97002
 */
public class ReIngredient {

    private String ingName;
    private String ingAmount;

    public String getIngName() {
        return ingName;
    }

    public boolean setIngName(String ingName) {
        System.out.println(ingName);
        if (ingName.length() <= 50) {
            this.ingName = ingName;
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
        if (ingAmount.length() <= 50) {
            this.ingAmount = ingAmount;
            return true;
        } else {
            return false;
        }
    }
public static int getReID(String ings){
int temp;
List<ReIngredient> list = new ArrayList<ReIngredient>();

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
            System.out.println("List_ID::  " + list);
        }
 
 for (ReIngredient rI : list) {
            String rITemp = rI.getIngName();
            
 
 
 return -1;
}
}
