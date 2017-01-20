/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.objects;

import javax.json.JsonArray;
import org.apache.taglibs.standard.lang.jstl.GreaterThanOperator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daca97002
 */
public class IngredientTest {

    public IngredientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Ingredient.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Ingredient instance = new Ingredient();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Ingredient.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Lorem ipsum dolor sit amet, consectetuer adipiscing";
        Ingredient instance = new Ingredient();
        boolean expResult = false;
        boolean result = instance.setName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getInfo method, of class Ingredient.
     */
    @Test
    public void testGetInfo() {
        System.out.println("getInfo");
        Ingredient instance = new Ingredient();
        String expResult = "";
        String result = instance.getInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInfo method, of class Ingredient.
     */
    @Test
    public void testSetInfo() {
        System.out.println("setInfo");
        String info = null;
        Ingredient instance = new Ingredient();
        boolean expResult = true;
        boolean result = instance.setInfo(info);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addIngredient method, of class Ingredient.
     */
    @Test
    public void testAddIngredient() {
        System.out.println("addIngredient");
        Ingredient ing = new Ingredient();
        ing.setName("Falskarn");
        ing.setInfo(null);
        int expResult = 0;
        int result = Ingredient.addIngredient(ing);
        assertTrue(result>expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateIng method, of class Ingredient.
     */
    @Test
    public void testUpdateIng() {
        System.out.println("updateIng");
        Ingredient ing = null;
        Ingredient expResult = null;
        Ingredient result = Ingredient.updateIng(ing);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteIng method, of class Ingredient.
     */
    @Test
    public void testDeleteIng() {
        System.out.println("deleteIng");
        int ingId = 0;
        boolean expResult = false;
        boolean result = Ingredient.deleteIng(ingId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllIngs method, of class Ingredient.
     */
    @Test
    public void testGetAllIngs() {
        System.out.println("getAllIngs");
        JsonArray expResult = null;
        JsonArray result = Ingredient.getAllIngs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
