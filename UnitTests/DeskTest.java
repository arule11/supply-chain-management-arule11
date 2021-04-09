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
    assertEquals("D0890",test.getID());
  }

  /**
     * Tests if the Constructor and getPrice() method works
     */
  @Test
  public void testConstructorAndGetPrice(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals(25,test.getPrice());
  }

  /**
     * Tests if the Constructor and getLegs() method works
     */
  @Test
  public void testConstructorAndGetLegs(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("N",test.getLegs());
  }

  /**
     * Tests if the Constructor and getDrawer() method works
     */

  @Test 
  public void testConstructorAndGetDrawer(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Y", test.getDrawer());
  }

  /**
     * Tests if the Constructor and getTop() method works
     */
  @Test 
  public void testConstructorAndGetTop(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("N", test.getTop());
  }


  /**
     * Tests if the Constructor and getType() method works
     */
  @Test 
  public void testConstructorAndGetType(){
    Desk test = new Desk("D0890", "Traditional", "N", "N", "Y", 25);
    assertEquals("Traditional", test.getType());
  }




}
