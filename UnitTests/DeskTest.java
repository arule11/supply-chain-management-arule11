/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien : alden.lien@ucalgary.ca
@version 1.1
@since 1.0
*/
package UnitTests;

import edu.ucalgary.ensf409.Desk;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DeskTest {

  /**
     * Tests if the Constructor and getID() method works
     */
  @Test
  public void testContructorAndGetID(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("ID was not set properly by the constructor","D0890",test.getID());
  }

  /**
     * Tests if the Constructor and getPrice() method works
     */
  @Test
  public void testConstructorAndGetPrice(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Price was not set properly by the constructor",25,test.getPrice());
  }

  /**
     * Tests if the Constructor and getLegs() method works
     */
  @Test
  public void testConstructorAndGetLegs(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Component status not set properly by the constructor","N",test.getLegs());
  }

  /**
     * Tests if the Constructor and getDrawer() method works
     */

  @Test 
  public void testConstructorAndGetDrawer(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Component status not set properly by the constructor","Y", test.getDrawer());
  }

  /**
     * Tests if the Constructor and getTop() method works
     */
  @Test 
  public void testConstructorAndGetTop(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Component status not set properly by the constructor","N", test.getTop());
  }


  /**
     * Tests if the Constructor and getType() method works
     */
  @Test 
  public void testConstructorAndGetType(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Type was not set properly by the constructor","Traditional", test.getType());
  }




}
