package edu.ucalgary.ensf409;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.*;

public class FurnitureTest{
    @Test
    public void testConstructor0 (){
        Furniture furniture = new Furniture("jdbc:mysql://localhost/inventory", "username", "ensf409");

        String expected = "ensf409";
        String result = furniture.getPassword();
        assertEquals("Retreived passwored failed", expected, result);
    }

}