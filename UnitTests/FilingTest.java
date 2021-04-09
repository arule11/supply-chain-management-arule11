/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien : alden.lien@ucalgary.ca
@version 1.1
@since 1.0
*/
package UnitTests;

import edu.ucalgary.ensf409.Filing;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FilingTest {

    /**
     * Tests if the Constructor and getID() method works
     */
    @Test
    public void testContructorAndGetID(){
      Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
      assertEquals("ID was not set properly by the constructor","F001",test.getID());
    }

    /**
     * Tests if the Constructor and getPrice() method works
     */
    @Test
    public void testConstructorAndGetPrice(){
        Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
      assertEquals("Price was not set properly by the constructor",50,test.getPrice());
    }

    /**
     * Tests if the Constructor and getType() method works
     */
    @Test 
  public void testConstructorAndGetType(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Type was not set properly by the constructor","Small", test.getType());
  }

  /**
     * Tests if the Constructor and getRails() method works
     */
  @Test 
  public void testConstructorAndGetRails(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Component status not set properly by the constructor","Y", test.getRails());
  }

  /**
     * Tests if the Constructor and getDrawers() method works
     */
  @Test 
  public void testConstructorAndGetDrawers(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Component status not set properly by the constructor","Y", test.getDrawers());
  }

  /**
     * Tests if the Constructor and getCabinet() method works
     */
  @Test 
  public void testConstructorAndGetCabinet(){
    Filing test = new Filing("F001", "Small", "Y", "Y", "N", 50);
    assertEquals("Component status not set properly by the constructor","N", test.getCabinet());
  }



}
