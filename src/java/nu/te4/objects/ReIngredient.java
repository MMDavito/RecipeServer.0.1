/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import java.util.ArrayList;
import java.util.List;
import nu.te4.support.ConnectionFactory;

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
    
    //RETURNS ALL INGREDIENT INFORMATION ASOSIATEAD WITH RECIPE
public static ArrayList<ReIngredient> getRecIngr(String recipeID){
List<ReIngredient> list = new ArrayList<>();


return (ArrayList<ReIngredient>) list;
}
}

