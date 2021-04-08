//package edu.ucalgary.ensf409;
package UnitTests;

import edu.ucalgary.ensf409.Chair;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ChairTest{

    /**
     * Tests if the Constructor and getID() method works
     */
    @Test
    public void testContructorAndGetID(){
      Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
      assertEquals("C0914",test.getID());
    }

    /**
     * Tests if the Constructor and getPrice() method works
     */
    @Test
    public void testConstructorAndGetPrice(){
        Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
      assertEquals(50,test.getPrice());
    }

    /**
     * Tests if the Constructor and getType() method works
     */
  @Test
  public void testConstructorAndGetType(){
    Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
    assertEquals("Task", test.getType());
  }


  /**
     * Tests if the Constructor and getLegs() method works
     */
  @Test 
  public void testConstructorAndGetLegs(){
    Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
    assertEquals("N", test.getLegs());
  }


  /**
     * Tests if the Constructor and getArms() method works
     */
  @Test 
  public void testConstructorAndGetArms(){
    Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
    assertEquals("N", test.getArms());
  }

  /**
     * Tests if the Constructor and getCushion() method works
     */
  @Test 
  public void testConstructorAndGetCushion(){
    Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
    assertEquals("Y", test.getCushion());
  }


  /**
     * Tests if the Constructor and getSeat() method works
     */
  @Test 
  public void testConstructorAndGetSeat(){
    Chair test = new Chair("C0914", "Task", "N", "N", "Y", "Y", 50);
    assertEquals("Y", test.getSeat());
  }
}
