/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : 
          Ivan Lou Tompong :
          Alden Lien : 
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Desk extends Furniture{

  private String legs; 
  private String top; 
  private String drawer; 
  private static String[] manufacturersID = new String[]{"001", "002", "004", "005"};

  /**
   * When the furniture type requested is a Desk, it calls the parent class Furniture to set the
   * ID of the Desk, the type of Desk, and then the price of the Desk, then the individual 
   * components of the Desk are assigned one of two characters, 'Y' if the Desk has said component
   * or a 'N' if said component is missing, based on the given arguments below. 
   * 
   * @param id : The ID used to identify the item
   * @param type : The type of Desk
   * @param leg : Contains 'Y' if desk has legs, 'N' if it doesn't
   * @param top : Contains 'Y' if desk has a top, 'N' if it doesn't
   * @param draw : Contains 'Y' if desk has a drawer, 'N' if it doesn't
   * @param price : Price of the item
   */
  public Desk(String id, String type, String leg, String top, String draw, int price){
    super(id, type, price);

    this.legs = leg;
    this.top = top;
    this.drawer = draw;
  }

  /**
   * @return Returns the variable legs
   */
  public String getLegs(){
    return this.legs;
  }

  /**
   * @return Returns the variable top
   */
  public String getTop(){
    return this.top;
  }

  /**
   * @return Returns the variable drawer
   */
  public String getDrawer(){
    return this.drawer;
  }
  /** 
   * @return Returns a String array called manufacturersID
   */
  public static String[] getManufacturers(){
    return manufacturersID;
  }
}
