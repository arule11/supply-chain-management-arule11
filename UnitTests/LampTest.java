/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien :
@version 1.1
@since 1.0
*/
package UnitTests;

import edu.ucalgary.ensf409.Lamp;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LampTest {

    /**
     * Tests if the Constructor and getID() method works
     */
    @Test
    public void testContructorAndGetID(){
      Lamp test = new Lamp("L013", "Desk", "Y", "N", 18);
      assertEquals("L013",test.getID());
    }

    /**
     * Tests if the Constructor and getPrice() method works
     */
    @Test
    public void testConstructorAndGetPrice(){
        Lamp test = new Lamp("L013", "Desk", "Y", "N", 18);
      assertEquals(18,test.getPrice());
    }

    /**
     * Tests if the Constructor and getType() method works
     */
    @Test 
  public void testConstructorAndGetType(){
    Lamp test = new Lamp("L013", "Desk", "Y", "N", 18);
    assertEquals("Desk", test.getType());
  }

  /**
     * Tests if the Constructor and getBulb() method works
     */
  @Test 
  public void testConstructorAndGetBulb(){
    Lamp test = new Lamp("L013", "Desk", "Y", "N", 18);
    assertEquals("N", test.getBulb());
  }

  /**
     * Tests if the Constructor and getBase() method works
     */
  @Test 
  public void testConstructorAndGetBase(){
    Lamp test = new Lamp("L013", "Desk", "Y", "N", 18);
    assertEquals("Y", test.getBase());
  }




}
