package edu.ucalgary.ensf409;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FilingTest {

    /**
     * Tests if the Constructor and getID() method works
     */
    @Test
    public void testContructorAndGetID(){
      Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
      assertEquals("F001",test.getID());
    }
  
    /**
     * Tests if the Constructor and getPrice() method works
     */
    @Test
    public void testConstructorAndGetPrice(){
        Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
      assertEquals(50,test.getPrice());
    }
  
    /**
     * Tests if the Constructor and getType() method works
     */
    @Test 
  public void testConstructorAndGetType(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Small", test.getType());
  }

  /**
     * Tests if the Constructor and getRails() method works
     */
  @Test 
  public void testConstructorAndGetRails(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Y", test.getRails());
  }

  /**
     * Tests if the Constructor and getDrawers() method works
     */
  @Test 
  public void testConstructorAndGetDrawers(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Y", test.getDrawers());
  }

  /**
     * Tests if the Constructor and getCabinet() method works
     */
  @Test 
  public void testConstructorAndGetCabinet(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("N", test.getCabinet());
  }


  
}
